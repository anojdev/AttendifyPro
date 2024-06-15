package edu.miu.attendifypro.controller;

import edu.miu.attendifypro.dto.CourseDto;
import edu.miu.attendifypro.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        CourseDto courseDto = courseService.getCourse(id);
        return new ResponseEntity<CourseDto>(courseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody CourseDto data) {
        CourseDto courseDto = courseService.createCourse(data);
        if(courseDto==null)
            return new ResponseEntity<String>("Could not save data", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<CourseDto>(courseDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable Long id,@RequestBody CourseDto data) {
        if(id!=data.getId())
            return new ResponseEntity<String>("Id do not match for body and url", HttpStatus.BAD_REQUEST);

        CourseDto courseDto = courseService.updateCourse(id,data);
        if(courseDto==null)
            return new ResponseEntity<String>("Could not update data", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<CourseDto>(courseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if(id==null)
            return new ResponseEntity<String>("Please provide course id", HttpStatus.BAD_REQUEST);

        CourseDto courseDto = courseService.deleteCourse(id);
        if(courseDto==null)
            return new ResponseEntity<String>("Could not delete data", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<CourseDto>(courseDto, HttpStatus.OK);
    }
}
