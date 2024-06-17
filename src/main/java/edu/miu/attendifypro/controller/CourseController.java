package edu.miu.attendifypro.controller;

import edu.miu.attendifypro.dto.response.CourseResponse;
import edu.miu.attendifypro.dto.request.CourseCreateRequest;
import edu.miu.attendifypro.dto.request.CourseUpdateRequest;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.dto.response.common.ApiResponse;
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
@RequestMapping("/course")
@CrossOrigin
public class CourseController {
    private final CourseService courseService;


    private final MessagingService messagingService;

    public CourseController(CourseService courseService, MessagingService messagingService) {
        this.courseService = courseService;
        this.messagingService = messagingService;
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses() {
        ServiceResponse<List<CourseResponse>> serviceRsp= courseService.getAllCourses();
        ApiResponse<List<CourseResponse>> apiResponse = ApiResponse.<List<CourseResponse>>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"course"}));
        return new ResponseEntity<ApiResponse<List<CourseResponse>>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }
    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<CourseResponse>>> getCourses(Pageable pageableReq) {
        Pageable pageable = PageRequest.of(pageableReq.getPageNumber()>0? pageableReq.getPageNumber()-1 : 0,
                pageableReq.getPageSize() ,
                pageableReq.getSort());
        ServiceResponse<Page<CourseResponse>> coursePage= courseService.getCoursePage(pageable);
        ApiResponse<Page<CourseResponse>> apiResponse = ApiResponse.<Page<CourseResponse>>builder().status(false)
                .code(coursePage.getStatusCode().name()).build();
        if (coursePage.getData().isPresent()) {
            apiResponse.setData(coursePage.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(coursePage, new String[]{"course"}));
        return new ResponseEntity<ApiResponse<Page<CourseResponse>>>(apiResponse,
                coursePage.getStatusCode().getHttpStatusCode());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourse(@PathVariable Long id, HttpServletRequest request) {
        ServiceResponse<CourseResponse> serviceRsp= courseService.getCourse(id);
        ApiResponse<CourseResponse> apiResponse = ApiResponse.<CourseResponse>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"course"}));
        return new ResponseEntity<ApiResponse<CourseResponse>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<CourseResponse>> create(@Valid @RequestBody CourseCreateRequest courseCreateRequest,
                                                              HttpServletRequest request) {
        ServiceResponse<CourseResponse> serviceRsp= courseService.createCourse(courseCreateRequest);
        ApiResponse<CourseResponse> apiResponse = ApiResponse.<CourseResponse>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"course"}));
        return new ResponseEntity<ApiResponse<CourseResponse>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> update(@Valid @RequestBody CourseUpdateRequest courseUpdateRequest,
                                                              @PathVariable Long id,
                                                              HttpServletRequest request) {
        ServiceResponse<CourseResponse> serviceRsp= courseService.updateCourse(id,courseUpdateRequest);
        ApiResponse<CourseResponse> apiResponse = ApiResponse.<CourseResponse>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"course"}));
        return new ResponseEntity<ApiResponse<CourseResponse>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long id,
                                                         HttpServletRequest request) {
        ServiceResponse<Boolean> serviceRsp= courseService.deleteCourse(id);
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
