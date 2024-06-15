package edu.miu.attendifypro.mapper;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.dto.CourseCreateRequest;
import edu.miu.attendifypro.dto.CourseDto;
import edu.miu.attendifypro.dto.CourseUpdateRequest;
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

    CourseDto courseToCourseDto(Course course);
    Course courseDtoToCours(CourseDto coursedto);

    @Mapping(target = "prerequisites", ignore = true)
    Course courseCreateRequestToCourse(CourseCreateRequest courseCreateRequest);

    @Mapping(target = "prerequisites", ignore = true)
    Course courseCreateRequestToCourse(CourseUpdateRequest courseUpdateRequest);
}