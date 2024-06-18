package edu.miu.attendifypro.controller.studentview;

import edu.miu.attendifypro.dto.response.CourseOfferingResponse;
import edu.miu.attendifypro.dto.response.common.ApiResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.service.CourseOfferingService;
import edu.miu.attendifypro.service.MessagingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student-view/course-offerings")
@CrossOrigin
public class courseOfferingsController {
    private final CourseOfferingService courseOfferingService;
    private final MessagingService messagingService;
    public courseOfferingsController(CourseOfferingService courseOfferingService,
                                    MessagingService messagingService) {
        this.courseOfferingService = courseOfferingService;
        this.messagingService = messagingService;
    }
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CourseOfferingResponse>>> getAllCourseOffering() {
        ServiceResponse<List<CourseOfferingResponse>> serviceRsp= courseOfferingService.getAllCourseOfferings();
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
