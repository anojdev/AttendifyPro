package edu.miu.attendifypro.mapper;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.domain.Location;
import edu.miu.attendifypro.dto.request.CourseCreateRequest;
import edu.miu.attendifypro.dto.request.CourseUpdateRequest;
import edu.miu.attendifypro.dto.request.LocationCreateRequest;
import edu.miu.attendifypro.dto.request.LocationUpdateRequest;
import edu.miu.attendifypro.dto.response.CourseResponse;
import edu.miu.attendifypro.dto.response.LocationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationDtoMapper {
    LocationDtoMapper locationdtoMapper =
            Mappers.getMapper(LocationDtoMapper.class);
    @Mapping(target = "locationType", ignore = true)
    Location locationCreateRequestToLocation(LocationCreateRequest locationCreateRequest);
    @Mapping(target = "locationType", ignore = true)
    Location locationCreateRequestToLocation(LocationUpdateRequest locationUpdateRequest);
    //Location DTO
    LocationResponse locationToLocationDto(Location location);
    Location locationDtoToLocation(LocationResponse locationdto);
}
