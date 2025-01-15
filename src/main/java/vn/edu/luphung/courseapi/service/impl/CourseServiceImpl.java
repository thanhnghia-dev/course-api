package vn.edu.luphung.courseapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.luphung.courseapi.dto.CourseDTO;
import vn.edu.luphung.courseapi.exception.ResourceNotFoundException;
import vn.edu.luphung.courseapi.model.Course;
import vn.edu.luphung.courseapi.repository.CourseRepository;
import vn.edu.luphung.courseapi.service.CourseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course saveCourse(CourseDTO courseDTO) {
        Course course = new Course();
        course.setCourseId(courseDTO.getCourseId());
        course.setName(courseDTO.getName());
        course.setStatus((byte) 1);
        course.setCreatedAt(LocalDateTime.now());

        return courseRepository.save(course);
    }

    @Override
    public List<CourseDTO> getCourses() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private CourseDTO convertToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseId(course.getCourseId());
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setStatus(course.getStatus());
        courseDTO.setCreatedAt(course.getCreatedAt());
        courseDTO.setUpdatedAt(course.getUpdatedAt());

        return courseDTO;
    }

    @Override
    public Course getCourseByID(Integer id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Course", "Id", id));
    }

    @Override
    public Course updateCourse(Integer id, CourseDTO courseDTO) {
        Course existingCourse = courseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Course", "Id", id));

        existingCourse.setCourseId(courseDTO.getCourseId() != null ? courseDTO.getCourseId() : existingCourse.getCourseId());
        existingCourse.setName(courseDTO.getName() != null ? courseDTO.getName() : existingCourse.getName());
        existingCourse.setStatus(courseDTO.getStatus() != 0 ? courseDTO.getStatus() : existingCourse.getStatus());
        existingCourse.setUpdatedAt(LocalDateTime.now());

        return courseRepository.save(existingCourse);
    }

    @Override
    public void deleteCourseByID(Integer id) {
        courseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Course", "Id", id));

        courseRepository.deleteById(id);
    }
}
