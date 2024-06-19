package edu.miu.attendifypro.service.persistence;

import edu.miu.attendifypro.domain.StudentCourseSelection;

import java.util.List;

public interface StudentCourseSelectionPersistenceService {
    List<StudentCourseSelection> findByStudentIdAndCourseOfferingId(String studentId, long courseOfferingId);

    List<StudentCourseSelection> findByStudentId(String studentId);
    List<StudentCourseSelection> getOfferingStartingInNDays(int i);

}
