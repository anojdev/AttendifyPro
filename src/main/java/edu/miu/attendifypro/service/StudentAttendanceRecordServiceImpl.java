package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.AppStatusCode;
import edu.miu.attendifypro.domain.StudentAttendanceRecord;
import edu.miu.attendifypro.dto.response.StudentAttendanceRecordResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.mapper.StudentAttendanceRecordDtoMapper;
import edu.miu.attendifypro.service.persistence.StudentAttendanceRecordPersistenceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentAttendanceRecordServiceImpl implements StudentAttendanceRecordService {
    private final StudentAttendanceRecordPersistenceService studentAttendanceRecordPersistenceService;

    public StudentAttendanceRecordServiceImpl(StudentAttendanceRecordPersistenceService studentAttendanceRecordPersistenceService) {
        this.studentAttendanceRecordPersistenceService = studentAttendanceRecordPersistenceService;
    }
    @Override
    public ServiceResponse<List<StudentAttendanceRecordResponse>> getStudentAttendanceRecord(String studentId) {
        try {
            List<StudentAttendanceRecord> studentAttendanceList = studentAttendanceRecordPersistenceService.getAttendanceRecordsByStudentId(studentId);
            List<StudentAttendanceRecordResponse> studentAttendanceResponses = studentAttendanceList.stream().map(StudentAttendanceRecordDtoMapper.dtoMapper::studentAttendanceRecordResponse).toList();
            return ServiceResponse.of(studentAttendanceResponses, AppStatusCode.S20000);
        }catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50001);
        }
    }
}
