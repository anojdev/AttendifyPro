package edu.miu.attendifypro.service;
import edu.miu.attendifypro.dto.request.LocationCreateRequest;
import edu.miu.attendifypro.dto.request.LocationUpdateRequest;
import edu.miu.attendifypro.dto.response.LocationResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface LocationService {
    ServiceResponse<LocationResponse> getLocation(Long id);

    ServiceResponse<Page<LocationResponse>> getLocationPage(Pageable pageable);

    ServiceResponse<LocationResponse> createLocation(LocationCreateRequest locationCreateRequest);

    ServiceResponse<LocationResponse> updateLocation(Long id, LocationUpdateRequest locationUpdateRequest);

    ServiceResponse<Boolean> deleteLocation(Long id);

    ServiceResponse<List<LocationResponse>> getAllLocations();
}
