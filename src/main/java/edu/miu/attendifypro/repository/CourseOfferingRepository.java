package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kush
 */
public interface CourseOfferingRepository extends JpaRepository<CourseOffering,Long> {
}
