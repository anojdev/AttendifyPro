package edu.miu.attendifypro.controller.student;

import edu.miu.attendifypro.dto.response.AttendanceReportDto;
import edu.miu.attendifypro.dto.response.CourseOfferingResponse;
import edu.miu.attendifypro.dto.response.StudentCourseSelectionResponse;
import edu.miu.attendifypro.dto.response.common.ApiResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.dto.response.report.Report1Response;
import edu.miu.attendifypro.service.AttendanceService;
import edu.miu.attendifypro.service.CourseOfferingService;
import edu.miu.attendifypro.service.MessagingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student-view/course-offering")
@CrossOrigin
public class StudentCourseOfferingController {
    private final CourseOfferingService courseOfferingService;

    private final AttendanceService attendanceService;
    private final MessagingService messagingService;

    public StudentCourseOfferingController(CourseOfferingService courseOfferingService, AttendanceService attendanceService, MessagingService messagingService) {
        this.courseOfferingService = courseOfferingService;
        this.attendanceService = attendanceService;
        this.messagingService = messagingService;
    }

    @GetMapping("/{offeringId}/attendance")
    public ResponseEntity<ApiResponse<List<AttendanceReportDto>>> getStudentAttendanceRecords(@PathVariable Long offeringId) {
        ServiceResponse<List<AttendanceReportDto>> serviceRsp= attendanceService.getStudentAttendanceRecords(offeringId);
        ApiResponse<List<AttendanceReportDto>> apiResponse = ApiResponse
                .<List<AttendanceReportDto>>builder()
                .status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"Student attendance"}));
        return new ResponseEntity<ApiResponse<List<AttendanceReportDto>>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Report1Response>>> getStudentCourseOffering() {
        ServiceResponse<List<Report1Response>> serviceRsp= courseOfferingService.getStudentCourseOffering();

        ApiResponse<List<Report1Response>> apiResponse = ApiResponse
                .<List<Report1Response>>builder()
                .status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"CourseOffering"}));
        return new ResponseEntity<ApiResponse<List<Report1Response>>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }

    @GetMapping("/{offeringId}")
    public ResponseEntity<ApiResponse<CourseOfferingResponse>> getCourseOfferingById(@PathVariable Long offeringId) {
        ServiceResponse<CourseOfferingResponse> serviceRsp= courseOfferingService.getCourseOfferingById(offeringId);
        ApiResponse<CourseOfferingResponse> apiResponse = ApiResponse
                .<CourseOfferingResponse>builder()
                .status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"CourseOffering"}));
        return new ResponseEntity<ApiResponse<CourseOfferingResponse>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());

    }
}
