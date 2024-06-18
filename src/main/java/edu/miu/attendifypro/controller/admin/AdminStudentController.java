package edu.miu.attendifypro.controller.admin;

import edu.miu.attendifypro.dto.response.StudentResponse;
import edu.miu.attendifypro.dto.response.common.ApiResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.service.MessagingService;
import edu.miu.attendifypro.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-view/students")
@CrossOrigin
public class AdminStudentController {
    private final StudentService studentService;
    private final MessagingService messagingService;

    public AdminStudentController(StudentService studentService,
                                  MessagingService messagingService) {
        this.studentService = studentService;
        this.messagingService = messagingService;
    }

    @GetMapping("/{studentID}")
    public ResponseEntity<ApiResponse<StudentResponse>> getCourseOfferingAttendance(@PathVariable String studentID) {
        ServiceResponse<StudentResponse> serviceRsp= studentService.getStudent(studentID);

        ApiResponse<StudentResponse> apiResponse = ApiResponse
                .<StudentResponse>builder()
                .status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"CourseOffering"}));
        return new ResponseEntity<ApiResponse<StudentResponse>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }
}
