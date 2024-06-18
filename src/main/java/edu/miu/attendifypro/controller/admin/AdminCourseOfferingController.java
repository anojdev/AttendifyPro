package edu.miu.attendifypro.controller.admin;

import edu.miu.attendifypro.dto.response.CourseOfferingResponse;
import edu.miu.attendifypro.dto.response.common.ApiResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.service.CourseOfferingService;
import edu.miu.attendifypro.service.MessagingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin-view/course-offering")
@CrossOrigin
public class AdminCourseOfferingController {
    private final CourseOfferingService courseOfferingService;
    private final MessagingService messagingService;

    public AdminCourseOfferingController(CourseOfferingService courseOfferingService,
                                         MessagingService messagingService) {
        this.courseOfferingService = courseOfferingService;
        this.messagingService=messagingService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CourseOfferingResponse>>> getAllCourseOffering(@RequestParam(required = false) String date) {
        ServiceResponse<List<CourseOfferingResponse>> serviceRsp= courseOfferingService.filterCourseOffering(date);

        ApiResponse<List<CourseOfferingResponse>> apiResponse = ApiResponse
                .<List<CourseOfferingResponse>>builder()
                .status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"CourseOffering"}));
        return new ResponseEntity<ApiResponse<List<CourseOfferingResponse>>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }
}
