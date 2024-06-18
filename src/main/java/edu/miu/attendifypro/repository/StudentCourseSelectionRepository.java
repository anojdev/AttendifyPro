package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.StudentCourseSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCourseSelectionRepository extends JpaRepository<StudentCourseSelection,Long> {

//    @Query("select s from " +
//            "StudentCourseSelection s " +
//            "join fetch s.student " +
//            "join fetch s.courseOffering " +
//            "where s.courseOffering.id=:offeringId and s.student.studentId=:studentId")
    @Query("SELECT DISTINCT s FROM StudentCourseSelection s " +
            "JOIN FETCH s.student st " +
            "JOIN FETCH s.courseOffering co " +
            "WHERE co.id = :offeringId AND st.studentId = :studentId")
    List<StudentCourseSelection> findByStudentIdAndCourseOfferingId(@Param("studentId") String studentId,@Param("offeringId") Long offeringId);
}
