package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.AppStatusCode;
import edu.miu.attendifypro.domain.Location;
import edu.miu.attendifypro.domain.LocationType;
import edu.miu.attendifypro.dto.request.LocationCreateRequest;
import edu.miu.attendifypro.dto.request.LocationUpdateRequest;
import edu.miu.attendifypro.dto.response.LocationResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.service.LocationServiceImpl;
import edu.miu.attendifypro.service.persistence.LocationPersistenceService;
import edu.miu.attendifypro.service.persistence.LocationTypePersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceImplTest {

    @Mock
    private LocationPersistenceService persistenceService;

    @Mock
    private LocationTypePersistenceService locationTypePersistenceService;

    @InjectMocks
    private LocationServiceImpl locationService;

    @BeforeEach
    void setUp() {
        // You can set up your mocks here if needed
    }

    @Test
    void testGetAllLocations() {
        List<Location> locations = List.of(new Location(), new Location());
        when(persistenceService.findAll()).thenReturn(locations);

        ServiceResponse<List<LocationResponse>> response = locationService.getAllLocations();

        assertEquals(AppStatusCode.S20000, response.getStatusCode());
        assertEquals(locations.size(), response.getData().get().size());
    }

    @Test
    void testGetLocation() {
        Location location = new Location();
        when(persistenceService.findById(anyLong())).thenReturn(Optional.of(location));

        ServiceResponse<LocationResponse> response = locationService.getLocation(1L);

        assertEquals(AppStatusCode.S20005, response.getStatusCode());
    }

    @Test
    void testGetLocationNotFound() {
        when(persistenceService.findById(anyLong())).thenReturn(Optional.empty());

        ServiceResponse<LocationResponse> response = locationService.getLocation(1L);

        assertEquals(AppStatusCode.E40004, response.getStatusCode());
    }

    @Test
    void testGetLocationPage() {
        Page<Location> page = mock(Page.class);
        when(persistenceService.findAll(any(Pageable.class))).thenReturn(page);

        ServiceResponse<Page<LocationResponse>> response = locationService.getLocationPage(Pageable.unpaged());

        assertEquals(AppStatusCode.S20000, response.getStatusCode());
    }

    @Test
    void testCreateLocation() {
        LocationCreateRequest request = new LocationCreateRequest();
        request.setLocationType(1L);
        when(locationTypePersistenceService.findById(anyLong())).thenReturn(Optional.of(new LocationType()));

        ServiceResponse<LocationResponse> response = locationService.createLocation(request);

        assertEquals(AppStatusCode.S20001, response.getStatusCode());
    }

    @Test
    void testCreateLocationBadRequest() {
        LocationCreateRequest request = new LocationCreateRequest();
        request.setLocationType(1L);
        when(locationTypePersistenceService.findById(anyLong())).thenReturn(Optional.empty());

        ServiceResponse<LocationResponse> response = locationService.createLocation(request);

        assertEquals(AppStatusCode.E40002, response.getStatusCode());
    }

    @Test
    void testUpdateLocation() {
        LocationUpdateRequest request = new LocationUpdateRequest();
        when(persistenceService.findById(anyLong())).thenReturn(Optional.of(new Location()));

        ServiceResponse<LocationResponse> response = locationService.updateLocation(1L, request);

        assertEquals(AppStatusCode.S20002, response.getStatusCode());
    }

    @Test
    void testDeleteLocation() {
        Location location = new Location();
        when(persistenceService.findById(anyLong())).thenReturn(Optional.of(location));

        ServiceResponse<Boolean> response = locationService.deleteLocation(1L);

        assertEquals(AppStatusCode.S20003, response.getStatusCode());
        assertTrue(response.getData().get());
    }

    @Test
    void testDeleteLocationNotFound() {
        when(persistenceService.findById(anyLong())).thenReturn(Optional.empty());

        ServiceResponse<Boolean> response = locationService.deleteLocation(1L);

        assertEquals(AppStatusCode.E40004, response.getStatusCode());
    }
}
