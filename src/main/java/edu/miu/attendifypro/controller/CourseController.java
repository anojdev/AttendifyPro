package edu.miu.attendifypro.controller;

import edu.miu.attendifypro.dto.CourseDto;
import edu.miu.attendifypro.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody Map<String, String> data) {
        Long courseName = Long.valueOf(data.get("accountNumber"));
        String customerName = data.get("customerName");
        CourseDto dto = new CourseDto();
        CourseDto courseDto = courseService.createCourse(dto);
        return new ResponseEntity<CourseDto>(courseDto, HttpStatus.OK);
    }
}
