package edu.miu.attendifypro.service;

import edu.miu.attendifypro.dto.response.common.ServiceResponse;

public interface AttendanceService {

    ServiceResponse<String> getAttendanceRecords(Long offeringId);
}
