package edu.miu.attendifypro.controller;

import edu.miu.attendifypro.dto.CourseCreateRequest;
import edu.miu.attendifypro.dto.CourseDto;
import edu.miu.attendifypro.dto.CourseUpdateRequest;
import edu.miu.attendifypro.dto.ServiceResponse;
import edu.miu.attendifypro.dto.common.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<CourseDto>>> getAllCourses() {

        ServiceResponse<List<CourseDto>> serviceRsp= courseService.getAllCourses();
        ApiResponse<List<CourseDto>> apiResponse = ApiResponse.<List<CourseDto>>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"course"}));
        return new ResponseEntity<ApiResponse<List<CourseDto>>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }
    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<CourseDto>>> getCourses(Pageable pageableReq) {
        Pageable pageable = PageRequest.of(pageableReq.getPageNumber(), pageableReq.getPageSize() - 1,
                pageableReq.getSort());
        ServiceResponse<Page<CourseDto>> coursePage= courseService.getCoursePage(pageable);
        ApiResponse<Page<CourseDto>> apiResponse = ApiResponse.<Page<CourseDto>>builder().status(false)
                .code(coursePage.getStatusCode().name()).build();
        if (coursePage.getData().isPresent()) {
            apiResponse.setData(coursePage.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(coursePage, new String[]{"course"}));
        return new ResponseEntity<ApiResponse<Page<CourseDto>>>(apiResponse,
                coursePage.getStatusCode().getHttpStatusCode());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourse(@PathVariable Long id,HttpServletRequest request) {
        ServiceResponse<CourseDto> serviceRsp= courseService.getAccount(id);
        ApiResponse<CourseDto> apiResponse = ApiResponse.<CourseDto>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"course"}));
        return new ResponseEntity<ApiResponse<CourseDto>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<CourseDto>> create(@Valid @RequestBody CourseCreateRequest courseCreateRequest,
                                                       HttpServletRequest request) {
        ServiceResponse<CourseDto> serviceRsp= courseService.createCourse(courseCreateRequest);
        ApiResponse<CourseDto> apiResponse = ApiResponse.<CourseDto>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"course"}));
        return new ResponseEntity<ApiResponse<CourseDto>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> update(@Valid @RequestBody CourseUpdateRequest courseUpdateRequest,
                                                         @PathVariable Long id,
                                                         HttpServletRequest request) {
        ServiceResponse<CourseDto> serviceRsp= courseService.updateCourse(id,courseUpdateRequest);
        ApiResponse<CourseDto> apiResponse = ApiResponse.<CourseDto>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"course"}));
        return new ResponseEntity<ApiResponse<CourseDto>>(apiResponse,
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
