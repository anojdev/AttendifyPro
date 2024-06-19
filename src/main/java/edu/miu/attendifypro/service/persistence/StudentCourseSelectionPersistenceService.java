package edu.miu.attendifypro.service.persistence;

import edu.miu.attendifypro.domain.StudentCourseSelection;

import java.util.List;
import java.util.Optional;

public interface StudentCourseSelectionPersistenceService {
    List<StudentCourseSelection> findByStudentIdAndCourseOfferingId(String studentId, long courseOfferingId);
    List<StudentCourseSelection> findByStudentId(String studentId);
}
