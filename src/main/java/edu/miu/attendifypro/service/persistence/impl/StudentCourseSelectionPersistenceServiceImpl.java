package edu.miu.attendifypro.service.persistence.impl;

import edu.miu.attendifypro.domain.StudentCourseSelection;
import edu.miu.attendifypro.repository.CourseOfferingRepository;
import edu.miu.attendifypro.repository.StudentCourseSelectionRepository;
import edu.miu.attendifypro.service.persistence.StudentCourseSelectionPersistenceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseSelectionPersistenceServiceImpl implements StudentCourseSelectionPersistenceService {
    private final StudentCourseSelectionRepository repository;

    public StudentCourseSelectionPersistenceServiceImpl(StudentCourseSelectionRepository repository) {
        this.repository = repository;
    }

    public List<StudentCourseSelection> findByStudentIdAndCourseOfferingId(String studentId, long courseOfferingId){
        var d = repository.findByStudentIdAndCourseOfferingId(studentId,courseOfferingId);
        return d;
    }

    @Override
    public List<StudentCourseSelection> findByStudentId(String studentId) {
        return repository.findByStudentId(studentId);
    }
}
