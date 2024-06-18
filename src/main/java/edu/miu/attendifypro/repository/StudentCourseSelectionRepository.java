package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.StudentCourseSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author kush
 */
public interface StudentCourseSelectionRepository extends JpaRepository<StudentCourseSelection,Long> {

    @Query("SELECT scs FROM StudentCourseSelection scs " +
            "JOIN FETCH scs.student s " +
            "JOIN FETCH scs.courseOffering co " +
            "WHERE co.startDate > :targetDate")
    List<StudentCourseSelection> getOfferingStartingInNDays(LocalDate targetDate);
}
