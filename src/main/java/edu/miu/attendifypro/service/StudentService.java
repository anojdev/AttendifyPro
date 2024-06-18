package edu.miu.attendifypro.service;

import edu.miu.attendifypro.dto.request.StudentRequest;
import edu.miu.attendifypro.dto.response.StudentResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    ServiceResponse<StudentResponse> getStudent(String studentId);

    ServiceResponse<Page<StudentResponse>> getStudentPage(Pageable pageable);

    ServiceResponse<StudentResponse> createStudent(StudentRequest studentRequest);

    ServiceResponse<StudentResponse> updateStudent(String id, StudentRequest studentUpdateRequest);

    ServiceResponse<Boolean> deleteStudent(String id);

    ServiceResponse<List<StudentResponse>> getAllStudents();
}
