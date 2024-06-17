package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {
}
