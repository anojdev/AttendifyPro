package edu.miu.attendifypro.service.persistence;

import edu.miu.attendifypro.domain.StudentCourseSelection;

import java.util.List;

/**
 * @author kush
 */
public interface StudentCourseSelectionPersistenceService {

    List<StudentCourseSelection> getOfferingStartingInNDays(int i);
}
