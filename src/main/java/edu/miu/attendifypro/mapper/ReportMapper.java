package edu.miu.attendifypro.mapper;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.domain.CourseOffering;
import edu.miu.attendifypro.domain.StudentCourseSelection;
import edu.miu.attendifypro.domain.auth.Account;
import edu.miu.attendifypro.dto.response.AccountResponse;
import edu.miu.attendifypro.dto.response.report.CourseOfferingReport1Response;
import edu.miu.attendifypro.dto.response.report.CourseReportResponse;
import edu.miu.attendifypro.dto.response.report.Report1Response;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportMapper {
    ReportMapper reportMapper =
            Mappers.getMapper(ReportMapper.class);


    Report1Response studentCourseSelectionToReport1Response(StudentCourseSelection account);
    CourseReportResponse courseToCourseResponse(Course course);
    CourseOfferingReport1Response courseOffToCourseOffReport1Response(CourseOffering courseOffering);

}
