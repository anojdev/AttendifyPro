package edu.miu.attendifypro.controller;

import edu.miu.attendifypro.dto.request.StudentRequest;
import edu.miu.attendifypro.dto.response.StudentResponse;
import edu.miu.attendifypro.dto.response.common.ApiResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.service.MessagingService;
import edu.miu.attendifypro.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;


    private final MessagingService messagingService;

    public StudentController(StudentService studentService, MessagingService messagingService) {
        this.studentService = studentService;
        this.messagingService = messagingService;
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        ServiceResponse<List<StudentResponse>> serviceRsp= studentService.getAllStudents();
        ApiResponse<List<StudentResponse>> apiResponse = ApiResponse.<List<StudentResponse>>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"student"}));
        return new ResponseEntity<ApiResponse<List<StudentResponse>>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }
    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<StudentResponse>>> getStudents(Pageable pageableReq) {
        Pageable pageable = PageRequest.of(pageableReq.getPageNumber()>0? pageableReq.getPageNumber()-1 : 0,
                pageableReq.getPageSize() ,
                pageableReq.getSort());
        ServiceResponse<Page<StudentResponse>> studentPage= studentService.getStudentPage(pageable);
        ApiResponse<Page<StudentResponse>> apiResponse = ApiResponse.<Page<StudentResponse>>builder().status(false)
                .code(studentPage.getStatusCode().name()).build();
        if (studentPage.getData().isPresent()) {
            apiResponse.setData(studentPage.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(studentPage, new String[]{"student"}));
        return new ResponseEntity<ApiResponse<Page<StudentResponse>>>(apiResponse,
                studentPage.getStatusCode().getHttpStatusCode());
    }
    @GetMapping("/{studentId}")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudent(@PathVariable String studentId, HttpServletRequest request) {
        ServiceResponse<StudentResponse> serviceRsp= studentService.getStudent(studentId);
        ApiResponse<StudentResponse> apiResponse = ApiResponse.<StudentResponse>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"student"}));
        return new ResponseEntity<ApiResponse<StudentResponse>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<StudentResponse>> create(@Valid @RequestBody StudentRequest studentCreateRequest,
                                                              HttpServletRequest request) {
        ServiceResponse<StudentResponse> serviceRsp= studentService.createStudent(studentCreateRequest);
        ApiResponse<StudentResponse> apiResponse = ApiResponse.<StudentResponse>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"student"}));
        return new ResponseEntity<ApiResponse<StudentResponse>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> update(@Valid @RequestBody StudentRequest studentUpdateRequest,
                                                              @PathVariable String id,
                                                              HttpServletRequest request) {
        ServiceResponse<StudentResponse> serviceRsp= studentService.updateStudent(id,studentUpdateRequest);
        ApiResponse<StudentResponse> apiResponse = ApiResponse.<StudentResponse>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"student"}));
        return new ResponseEntity<ApiResponse<StudentResponse>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable String id,
                                                       HttpServletRequest request) {
        ServiceResponse<Boolean> serviceRsp= studentService.deleteStudent(id);
        ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"student"}));
        return new ResponseEntity<ApiResponse<Boolean>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());

    }

}
