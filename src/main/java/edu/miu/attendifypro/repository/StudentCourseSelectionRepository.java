package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.StudentCourseSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCourseSelectionRepository extends JpaRepository<StudentCourseSelection,Long> {


    @Query("SELECT DISTINCT s FROM StudentCourseSelection s " +
            "JOIN FETCH s.student st " +
            "JOIN FETCH s.courseOffering co " +
            "WHERE st.studentId = :studentId")
    List<StudentCourseSelection> findByStudentId(String studentId);

    @Query("SELECT DISTINCT s FROM StudentCourseSelection s " +
            "JOIN FETCH s.student st " +
            "JOIN FETCH s.courseOffering co " +
            "WHERE co.id = :offeringId AND st.studentId = :studentId")
    List<StudentCourseSelection> findByStudentIdAndCourseOfferingId(@Param("studentId") String studentId,@Param("offeringId") Long offeringId);

    @Query("SELECT scs FROM StudentCourseSelection scs " +
            "JOIN FETCH scs.student s " +
            "JOIN FETCH scs.courseOffering co " +
            "WHERE co.startDate > :targetDate")
    List<StudentCourseSelection> getOfferingStartingInNDays(LocalDate targetDate);



}
