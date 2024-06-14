package edu.miu.attendifypro.mapper;
import edu.miu.attendifypro.domain.Student;
import edu.miu.attendifypro.dto.StudentDto;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDto toDTO(Student student){
        return new StudentDto(
                student.getEntry(),
                student.getStudentId(),
                student.getApplicantId(),
                student.getFacultyAdvisor()
        );
    }

    public Student toEntity(StudentDto student){
        return new Student(
                student.getEntry(),
                student.getStudentId(),
                student.getApplicantId(),
                student.getFacultyAdvisor()
        );
    }
}
