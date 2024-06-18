package edu.miu.attendifypro.service;

import edu.miu.attendifypro.dto.response.AttendanceReportDto;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;

import java.util.List;

public interface AttendanceService {

    ServiceResponse<List<AttendanceReportDto>> getAttendanceRecords(Long offeringId);
}
