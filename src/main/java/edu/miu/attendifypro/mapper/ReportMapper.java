package edu.miu.attendifypro.mapper;

import edu.miu.attendifypro.domain.*;
import edu.miu.attendifypro.dto.response.report.*;
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

    CourseScheduleResponse courseToCourseScheduleResponse(Course course);
    CourseOfferingWithRosterResponse courseOfferingToRosterMapper(CourseOffering course);
    FacultyReportResponse facultyToFacultyReportResponse(Faculty faculty);
    StudentReportResponse studentToStudentReportResponse(Student faculty);
}
