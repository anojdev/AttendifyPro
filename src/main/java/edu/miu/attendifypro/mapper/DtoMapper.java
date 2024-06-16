package edu.miu.attendifypro.mapper;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.dto.response.CourseResponse;
import edu.miu.attendifypro.dto.request.CourseCreateRequest;
import edu.miu.attendifypro.dto.request.CourseUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author kush
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DtoMapper {
    DtoMapper dtoMapper =
            Mappers.getMapper(DtoMapper.class);

    CourseResponse courseToCourseDto(Course course);
    Course courseDtoToCourse(CourseResponse coursedto);

    @Mapping(target = "prerequisites", ignore = true)
    Course courseCreateRequestToCourse(CourseCreateRequest courseCreateRequest);

    @Mapping(target = "prerequisites", ignore = true)
    Course courseCreateRequestToCourse(CourseUpdateRequest courseUpdateRequest);
}