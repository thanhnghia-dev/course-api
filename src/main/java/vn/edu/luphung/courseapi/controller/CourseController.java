package vn.edu.luphung.courseapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.luphung.courseapi.dto.CourseDTO;
import vn.edu.luphung.courseapi.model.Course;
import vn.edu.luphung.courseapi.service.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/public/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    // Create a new Course
    @PostMapping()
    public ResponseEntity<Course> createCourse(@ModelAttribute CourseDTO course) {
        return new ResponseEntity<>(courseService.saveCourse(course), HttpStatus.CREATED);
    }

    // Get all Course
    @GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.getCourses();
    }

    // Get Course by id
    @GetMapping("{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable ("id") int id) {
        return new ResponseEntity<>(courseService.getCourseByID(id), HttpStatus.OK);
    }

    // Update Course by id
    @PutMapping("{id}")
    public ResponseEntity<Course> updateCourseById(@PathVariable ("id") int id,
                                                   @ModelAttribute CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.updateCourse(id, courseDTO), HttpStatus.OK);
    }

    // Delete Course by id
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCourseById(@PathVariable ("id") int id) {
        courseService.deleteCourseByID(id);
        return new ResponseEntity<>("Course " + id + " is deleted successfully!", HttpStatus.OK);
    }

}
