package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.domain.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseOfferingRepository extends JpaRepository<CourseOffering,Long> {
    @Query("select c.courses from CourseOffering c " +
            "where :date is null or" +
            "(c.startDate <= :date and c.endDate>=:date)"
            +" "
    )
    public List<Course> getCoursesByDate(@Param("date") LocalDate date);

}
