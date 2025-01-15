package vn.edu.luphung.courseapi.service;

import vn.edu.luphung.courseapi.dto.CourseDTO;
import vn.edu.luphung.courseapi.model.Course;

import java.util.List;

public interface CourseService {
    Course saveCourse(CourseDTO courseDTO);
    List<CourseDTO> getCourses();
    Course getCourseByID(Integer id);
    Course updateCourse(Integer id, CourseDTO courseDTO);
    void deleteCourseByID(Integer id);

}
