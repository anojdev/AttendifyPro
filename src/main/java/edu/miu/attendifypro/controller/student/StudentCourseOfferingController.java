package edu.miu.attendifypro.controller.student;

import edu.miu.attendifypro.dto.response.CourseOfferingResponse;
import edu.miu.attendifypro.dto.response.StudentCourseSelectionResponse;
import edu.miu.attendifypro.dto.response.common.ApiResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
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
    private final MessagingService messagingService;

    public StudentCourseOfferingController(CourseOfferingService courseOfferingService, MessagingService messagingService) {
        this.courseOfferingService = courseOfferingService;
        this.messagingService = messagingService;
    }

    @GetMapping("/{offeringId}")
    public ResponseEntity<ApiResponse<List<StudentCourseSelectionResponse>>> getCourseOfferingById(@PathVariable Long offeringId) {
        ServiceResponse<List<StudentCourseSelectionResponse>> serviceRsp= courseOfferingService.getStudentCourseOfferingById(offeringId);

        ApiResponse<List<StudentCourseSelectionResponse>> apiResponse = ApiResponse
                .<List<StudentCourseSelectionResponse>>builder()
                .status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"CourseOffering"}));
        return new ResponseEntity<ApiResponse<List<StudentCourseSelectionResponse>>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<StudentCourseSelectionResponse>>> getStudentCourseSelection() {
        ServiceResponse<List<StudentCourseSelectionResponse>> serviceRsp= courseOfferingService.getStudentCourseOffering();

        ApiResponse<List<StudentCourseSelectionResponse>> apiResponse = ApiResponse
                .<List<StudentCourseSelectionResponse>>builder()
                .status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"CourseOffering"}));
        return new ResponseEntity<ApiResponse<List<StudentCourseSelectionResponse>>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }

}
