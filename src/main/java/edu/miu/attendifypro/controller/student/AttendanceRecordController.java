package edu.miu.attendifypro.controller.student;

import edu.miu.attendifypro.dto.response.AttendanceReportDto;
import edu.miu.attendifypro.dto.response.StudentAttendanceRecordResponse;
import edu.miu.attendifypro.dto.response.common.ApiResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.service.AttendanceService;
import edu.miu.attendifypro.service.MessagingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/student-view")
@CrossOrigin
public class AttendanceRecordController {
    private final AttendanceService studentAttendanceRecordService;

    private final MessagingService messagingService;

    public AttendanceRecordController(AttendanceService studentAttendanceRecordService, MessagingService messagingService) {
        this.studentAttendanceRecordService = studentAttendanceRecordService;
        this.messagingService = messagingService;
    }

    @GetMapping("/attendance-records")
    public ResponseEntity<ApiResponse<List<StudentAttendanceRecordResponse>>> getStudentAttendanceRecord(Principal principal) {
        ServiceResponse<List<StudentAttendanceRecordResponse>> serviceRsp= studentAttendanceRecordService.getSingleStudentAttendanceRecord();
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
}
