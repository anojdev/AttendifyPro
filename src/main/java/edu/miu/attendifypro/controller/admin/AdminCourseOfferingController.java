package edu.miu.attendifypro.controller.admin;

import edu.miu.attendifypro.dto.response.CourseOfferingResponse;
import edu.miu.attendifypro.dto.response.common.ApiResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.dto.response.report.CourseScheduleResponse;
import edu.miu.attendifypro.service.AttendanceService;
import edu.miu.attendifypro.service.CourseOfferingService;
import edu.miu.attendifypro.service.MessagingService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/admin-view/course-offering")
@CrossOrigin
public class AdminCourseOfferingController {
    private final CourseOfferingService courseOfferingService;
    private final AttendanceService attendanceService;
    private final MessagingService messagingService;

    public AdminCourseOfferingController(CourseOfferingService courseOfferingService, AttendanceService attendanceService,
                                         MessagingService messagingService) {
        this.courseOfferingService = courseOfferingService;
        this.attendanceService = attendanceService;
        this.messagingService=messagingService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CourseScheduleResponse>>> getCourseOfferingAttendance(@RequestParam(required = false) String date) {
        ServiceResponse<List<CourseScheduleResponse>> serviceRsp= courseOfferingService.getCoursesByDate(date);

        ApiResponse<List<CourseScheduleResponse>> apiResponse = ApiResponse
                .<List<CourseScheduleResponse>>builder()
                .status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"CourseOffering"}));
        return new ResponseEntity<ApiResponse<List<CourseScheduleResponse>>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }

    @GetMapping("/{offeringId}/attendance")
    public ResponseEntity<InputStreamResource> getCourseOfferingAttendance(@PathVariable Long offeringId) {
        ServiceResponse<String> serviceRsp= attendanceService.getAttendanceRecords(offeringId);
        Path locationPath = Paths.get(serviceRsp.getData().get());
        InputStreamResource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(serviceRsp.getData().get()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + locationPath.getFileName().toString());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(locationPath.toFile().length()));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(locationPath.toFile().length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }
}
