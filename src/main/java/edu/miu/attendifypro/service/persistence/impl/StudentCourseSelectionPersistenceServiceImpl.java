package edu.miu.attendifypro.service.persistence.impl;

import edu.miu.attendifypro.domain.StudentCourseSelection;
import edu.miu.attendifypro.repository.StudentCourseSelectionRepository;
import edu.miu.attendifypro.service.persistence.StudentCourseSelectionPersistenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class StudentCourseSelectionPersistenceServiceImpl implements StudentCourseSelectionPersistenceService {

    private final StudentCourseSelectionRepository studentCourseSelectionRepository;


    public StudentCourseSelectionPersistenceServiceImpl(StudentCourseSelectionRepository studentCourseSelectionRepository) {
        this.studentCourseSelectionRepository = studentCourseSelectionRepository;
    }

    @Override
    public List<StudentCourseSelection> getOfferingStartingInNDays(int n) {
        LocalDate targetDate = LocalDate.now().minusDays(n);
        return studentCourseSelectionRepository.getOfferingStartingInNDays(targetDate);
    }
    public List<StudentCourseSelection> findByStudentIdAndCourseOfferingId(String studentId, long courseOfferingId){
        var d = studentCourseSelectionRepository.findByStudentIdAndCourseOfferingId(studentId,courseOfferingId);
        return d;
    }
}
