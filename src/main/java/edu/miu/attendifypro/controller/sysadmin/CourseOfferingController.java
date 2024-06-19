package edu.miu.attendifypro.controller.sysadmin;

import edu.miu.attendifypro.dto.request.CourseOfferingCreateRequest;
import edu.miu.attendifypro.dto.request.CourseOfferingUpdateRequest;
import edu.miu.attendifypro.dto.response.CourseOfferingResponse;
import edu.miu.attendifypro.dto.response.CourseResponse;
import edu.miu.attendifypro.dto.request.CourseCreateRequest;
import edu.miu.attendifypro.dto.request.CourseUpdateRequest;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.dto.response.common.ApiResponse;
import edu.miu.attendifypro.service.CourseOfferingService;
import edu.miu.attendifypro.service.CourseService;
import edu.miu.attendifypro.service.MessagingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys-admin/course-offering")
@CrossOrigin
public class CourseOfferingController {
    private final CourseOfferingService courseOfferingService;
    private final MessagingService messagingService;

    public CourseOfferingController(CourseOfferingService courseOfferingService,
                                    MessagingService messagingService) {
        this.courseOfferingService = courseOfferingService;
        this.messagingService = messagingService;
    }
    @GetMapping("/all")
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseOfferingResponse>> getCourseOfferingById(@PathVariable Long id) {
        ServiceResponse<CourseOfferingResponse> serviceRsp= courseOfferingService.getCourseOfferingById(id);
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

    @PostMapping("")
    public ResponseEntity<ApiResponse<CourseOfferingResponse>> create(@Valid @RequestBody CourseOfferingCreateRequest createRequest,
                                                              HttpServletRequest request) {
        ServiceResponse<CourseOfferingResponse> serviceRsp= courseOfferingService
                                    .createCourseOffering(createRequest);

        ApiResponse<CourseOfferingResponse> apiResponse = ApiResponse
                                .<CourseOfferingResponse>builder().status(false)
                                .code(serviceRsp.getStatusCode().name()).build();

        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"course"}));
        return new ResponseEntity<ApiResponse<CourseOfferingResponse>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseOfferingResponse>> update(@Valid @RequestBody CourseOfferingUpdateRequest updateRequest,
                                                              @PathVariable Long id,
                                                              HttpServletRequest request) {
        ServiceResponse<CourseOfferingResponse> serviceRsp= courseOfferingService.updateCourseOffering(id,updateRequest);
        ApiResponse<CourseOfferingResponse> apiResponse = ApiResponse.<CourseOfferingResponse>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"course"}));
        return new ResponseEntity<ApiResponse<CourseOfferingResponse>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long id,
                                                       HttpServletRequest request) {
        ServiceResponse<Boolean> serviceRsp= courseOfferingService.deleteCourseOffering(id);
        ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"course"}));
        return new ResponseEntity<ApiResponse<Boolean>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }
}
