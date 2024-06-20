package edu.miu.attendifypro.scheduledtasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.miu.attendifypro.domain.StudentCourseSelection;
import edu.miu.attendifypro.dto.EmailObject;
import edu.miu.attendifypro.messaging.SendMessage;
import edu.miu.attendifypro.service.persistence.StudentCourseSelectionPersistenceService;
import edu.miu.attendifypro.util.StaticUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author kush
 */
@Service
@Slf4j
public class CourseSelectionEmailDispatcher {

    @Value("${app.email.course-welcome.dispatch.interval:1}")
    private int courseWelcomeEmailDispatcherInterval;

    final
    SendMessage sendMessage;

    final
    StudentCourseSelectionPersistenceService persistenceService;

    public CourseSelectionEmailDispatcher(StudentCourseSelectionPersistenceService persistenceService, SendMessage sendMessage) {
        this.persistenceService = persistenceService;
        this.sendMessage = sendMessage;
    }


    @Scheduled(initialDelay = 10000L,fixedDelayString = "#{${app.email.course-welcome.dispatch.interval:1} * 1000*60}")
    public void dispatchWelcomeEmails(){
        List<StudentCourseSelection> courseSelections=persistenceService.getOfferingStartingInNDays(7);
        for (StudentCourseSelection courseSelection : courseSelections) {
            EmailObject object = new EmailObject();
            object.setFrom(courseSelection.getCourseOffering().getFaculty().getEmail());
            object.setTo(Collections.singletonList(courseSelection.getStudent().getEmail()));
            object.setSubject("Welcome to " + courseSelection.getCourseOffering().getCourses().getCourseName() + "-" + courseSelection.getCourseOffering().getCourses().getCourseCode());
            object.setBody("Dear Student, Welcome to the block.");
            try {
                sendMessage.dispatchEmailMessage(StaticUtils.mapper.writeValueAsString(object));
                courseSelection.setNotified(true);
                persistenceService.save(courseSelection);
            } catch (JsonProcessingException e) {
                log.error("Error parsing email object to String {}", e.getOriginalMessage());
            }
        }
    }
}
