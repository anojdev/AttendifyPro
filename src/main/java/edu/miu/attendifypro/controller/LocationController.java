package edu.miu.attendifypro.controller;

import edu.miu.attendifypro.dto.request.LocationCreateRequest;
import edu.miu.attendifypro.dto.request.LocationUpdateRequest;
import edu.miu.attendifypro.dto.response.CourseResponse;
import edu.miu.attendifypro.dto.request.CourseCreateRequest;
import edu.miu.attendifypro.dto.request.CourseUpdateRequest;
import edu.miu.attendifypro.dto.response.LocationResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.dto.response.common.ApiResponse;
import edu.miu.attendifypro.service.CourseService;
import edu.miu.attendifypro.service.LocationService;
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
@RequestMapping("/location")
@CrossOrigin
public class LocationController {
    private final LocationService locationService;


    private final MessagingService messagingService;

    public LocationController(LocationService locationService, MessagingService messagingService) {
        this.locationService = locationService;
        this.messagingService = messagingService;
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<LocationResponse>>> getAllLocations() {
        ServiceResponse<List<LocationResponse>> serviceRsp= locationService.getAllLocations();
        ApiResponse<List<LocationResponse>> apiResponse = ApiResponse.<List<LocationResponse>>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"course"}));
        return new ResponseEntity<ApiResponse<List<LocationResponse>>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }
    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<LocationResponse>>> getCourses(Pageable pageableReq) {
        Pageable pageable = PageRequest.of(pageableReq.getPageNumber()>0? pageableReq.getPageNumber()-1 : 0,
                pageableReq.getPageSize() ,
                pageableReq.getSort());
        ServiceResponse<Page<LocationResponse>> locationPage= locationService.getLocationPage(pageable);
        ApiResponse<Page<LocationResponse>> apiResponse = ApiResponse.<Page<LocationResponse>>builder().status(false)
                .code(locationPage.getStatusCode().name()).build();
        if (locationPage.getData().isPresent()) {
            apiResponse.setData(locationPage.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(locationPage, new String[]{"location"}));
        return new ResponseEntity<ApiResponse<Page<LocationResponse>>>(apiResponse,
                locationPage.getStatusCode().getHttpStatusCode());
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse<LocationResponse>> getCourse(@PathVariable Long id, HttpServletRequest request) {
//        ServiceResponse<LocationResponse> serviceRsp= locationService.getAccount(id);
//        ApiResponse<LocationResponse> apiResponse = ApiResponse.<LocationResponse>builder().status(false)
//                .code(serviceRsp.getStatusCode().name()).build();
//        if (serviceRsp.getData().isPresent()) {
//            apiResponse.setData(serviceRsp.getData().get());
//            apiResponse.setStatus(true);
//        }
//        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"course"}));
//        return new ResponseEntity<ApiResponse<LocationResponse>>(apiResponse,
//                serviceRsp.getStatusCode().getHttpStatusCode());
//    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<LocationResponse>> create(@Valid @RequestBody LocationCreateRequest locationCreateRequest,
                                                              HttpServletRequest request) {
        ServiceResponse<LocationResponse> serviceRsp= locationService.createLocation(locationCreateRequest);
        ApiResponse<LocationResponse> apiResponse = ApiResponse.<LocationResponse>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"location"}));
        return new ResponseEntity<ApiResponse<LocationResponse>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LocationResponse>> update(@Valid @RequestBody LocationUpdateRequest locationUpdateRequest,
                                                              @PathVariable Long id,
                                                              HttpServletRequest request) {
        ServiceResponse<LocationResponse> serviceRsp= locationService.updateLocation(id,locationUpdateRequest);
        ApiResponse<LocationResponse> apiResponse = ApiResponse.<LocationResponse>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"location"}));
        return new ResponseEntity<ApiResponse<LocationResponse>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long id,
                                                       HttpServletRequest request) {
        ServiceResponse<Boolean> serviceRsp= locationService.deleteLocation(id);
        ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder().status(false)
                .code(serviceRsp.getStatusCode().name()).build();
        if (serviceRsp.getData().isPresent()) {
            apiResponse.setData(serviceRsp.getData().get());
            apiResponse.setStatus(true);
        }
        apiResponse.setMessage(messagingService.getResponseMessage(serviceRsp, new String[]{"location"}));
        return new ResponseEntity<ApiResponse<Boolean>>(apiResponse,
                serviceRsp.getStatusCode().getHttpStatusCode());

    }

}
