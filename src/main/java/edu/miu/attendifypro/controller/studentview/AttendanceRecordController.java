package edu.miu.attendifypro.controller.studentview;


import edu.miu.attendifypro.dto.response.StudentAttendanceRecordResponse;
import edu.miu.attendifypro.dto.response.common.ApiResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.service.MessagingService;
import edu.miu.attendifypro.service.StudentAttendanceRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/student-view")
@CrossOrigin
public class AttendanceRecordController {
    private final StudentAttendanceRecordService studentAttendanceRecordService;

    private final MessagingService messagingService;

    public AttendanceRecordController(StudentAttendanceRecordService studentAttendanceRecordService, MessagingService messagingService) {
        this.studentAttendanceRecordService = studentAttendanceRecordService;
        this.messagingService = messagingService;
    }

    @GetMapping("/attendance-records")
    public ResponseEntity<ApiResponse<List<StudentAttendanceRecordResponse>>> getStudentAttendanceRecord(Principal principal) {
        // Assuming the student ID can be derived from the authenticated user's details
        String studentId = getStudentIdFromPrincipal(principal);

        ServiceResponse<List<StudentAttendanceRecordResponse>> serviceRsp= studentAttendanceRecordService.getStudentAttendanceRecord(studentId);
        ApiResponse<List<StudentAttendanceRecordResponse>> apiResponse = ApiResponse.<List<StudentAttendanceRecordResponse>>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();

        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"student-attendance-record"}));
        return new ResponseEntity<ApiResponse<List<StudentAttendanceRecordResponse>>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }


    private String getStudentIdFromPrincipal(Principal principal) {
        // Mock implementation, replace with actual logic to get student ID from Principal
        // For example, if you store the username as the student ID
        return String.valueOf(principal.getName());
    }
}
