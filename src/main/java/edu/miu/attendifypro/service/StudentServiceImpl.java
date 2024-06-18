package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.AppStatusCode;
import edu.miu.attendifypro.domain.Faculty;
import edu.miu.attendifypro.domain.Student;
import edu.miu.attendifypro.dto.request.StudentRequest;
import edu.miu.attendifypro.dto.response.StudentResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.mapper.StudentDtoMapper;
import edu.miu.attendifypro.repository.FacultyRepository;
import edu.miu.attendifypro.service.persistence.FacultyPersistenceService;
import edu.miu.attendifypro.service.persistence.StudentPersistenceService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentPersistenceService persistenceService;

    private final FacultyPersistenceService facultyPersistenceService;

    public StudentServiceImpl(StudentPersistenceService persistenceService, FacultyPersistenceService facultyPersistenceService) {
        this.persistenceService = persistenceService;
        this.facultyPersistenceService = facultyPersistenceService;
    }

    @Override
    public ServiceResponse<List<StudentResponse>> getAllStudents() {
        try {
            List<Student> studentList = persistenceService.findAll();
            List<StudentResponse> studentResponses =studentList.stream().map(StudentDtoMapper.dtoMapper::studentToStudentResponse).toList();
            return ServiceResponse.of(studentResponses, AppStatusCode.S20000);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50002);
        }
    }

    @Override
    public ServiceResponse<StudentResponse> getStudent(String studentId) {
        try {
            Optional<Student> studentOpt = persistenceService.findByStudentId(studentId);
            return studentOpt.map(student -> ServiceResponse.of(StudentDtoMapper.dtoMapper.studentToStudentResponse(student), AppStatusCode.S20005)).orElseGet(() -> ServiceResponse.of(AppStatusCode.E40004));
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50001);
        }
    }

    @Override
    public ServiceResponse<Page<StudentResponse>> getStudentPage(Pageable pageable) {
        try {
            Page<Student> studentPage = persistenceService.findAll(pageable);
            Page<StudentResponse> studentDtoPage = studentPage.map(StudentDtoMapper.dtoMapper::studentToStudentResponse);
            return ServiceResponse.of(studentDtoPage, AppStatusCode.S20000);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50002);
        }
    }

    @Override
    public ServiceResponse<StudentResponse> createStudent(StudentRequest studentRequest) {

        Optional<Student> studentOpt=persistenceService.findByStudentId(studentRequest.getStudentId());
        if(studentOpt.isPresent()){
            return ServiceResponse.of(AppStatusCode.E40006,List.of("student.id.exists"));
        }

        try {
            Student student = StudentDtoMapper.dtoMapper.studentRequestToStudent(studentRequest);

            Optional<Faculty> facultyAdvisor = facultyPersistenceService.findById(studentRequest.getFacultyAdvisorId());
            facultyAdvisor.ifPresent(student::setFacultyAdvisor);

            persistenceService.save(student);
            return ServiceResponse.of(StudentDtoMapper.dtoMapper.studentToStudentResponse(student),AppStatusCode.S20001);

        }catch(DataIntegrityViolationException ex){
            return ServiceResponse.of(AppStatusCode.E40002);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50003);
        }
    }

    @Override
    public ServiceResponse<StudentResponse> updateStudent(String id, StudentRequest studentUpdateRequest) {
        Optional<Student> studentOpt=persistenceService.findByStudentId(id);
        if(studentOpt.isEmpty()){
            return ServiceResponse.of(AppStatusCode.E40006,List.of("student.id.doesn't.exists"));
        }

            try {
                Student student = studentOpt.get();
                student.setApplicantId(studentUpdateRequest.getApplicantId());
                student.setFirstName(studentUpdateRequest.getFirstName());
                student.setLastName(studentUpdateRequest.getLastName());
                student.setEmail(studentUpdateRequest.getEmail());
                student.setEntry(studentUpdateRequest.getEntry());
                student.setGender(studentUpdateRequest.getGender());
                student.setBirthDate(studentUpdateRequest.getBirthDate());

            Optional<Faculty> facultyAdvisor = facultyPersistenceService.findById(studentUpdateRequest.getFacultyAdvisorId());
            facultyAdvisor.ifPresent(student::setFacultyAdvisor);

                persistenceService.save(student);
                return ServiceResponse.of(StudentDtoMapper.dtoMapper.studentToStudentResponse(student),AppStatusCode.S20002);
            }
            catch (Exception e){
                return ServiceResponse.of(AppStatusCode.E50002);
            }
        }

    @Override
    public ServiceResponse<Boolean> deleteStudent(String id) {
        try {
            Optional<Student> studentOpt = persistenceService.findByStudentId(id);
            if(studentOpt.isPresent()) {
                persistenceService.delete(studentOpt.get());
                return ServiceResponse.of(true,AppStatusCode.S20003);
            }
            else{
                return ServiceResponse.of(AppStatusCode.E40004);
            }
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50005);
        }
    }
}
