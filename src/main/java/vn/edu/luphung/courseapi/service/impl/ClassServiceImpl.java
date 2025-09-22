package vn.edu.luphung.courseapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.luphung.courseapi.dto.ClassDTO;
import vn.edu.luphung.courseapi.dto.CourseDTO;
import vn.edu.luphung.courseapi.exception.ResourceNotFoundException;
import vn.edu.luphung.courseapi.model.Class;
import vn.edu.luphung.courseapi.model.Course;
import vn.edu.luphung.courseapi.repository.ClassRepository;
import vn.edu.luphung.courseapi.repository.CourseRepository;
import vn.edu.luphung.courseapi.service.ClassService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Class saveClass(int courseId, ClassDTO classDTO) {
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new ResourceNotFoundException("Course", "Id", classDTO.getCourse().getId()));

        Class classroom = new Class();
        classroom.setClassId(classDTO.getClassId());
        classroom.setCourse(course);
        classroom.setName(classDTO.getName());
        classroom.setStartDate(classDTO.getStartDate());
        classroom.setEndDate(classDTO.getEndDate());
        classroom.setExamDate(classDTO.getExamDate());
        classroom.setStatus((byte) 1);
        classroom.setCreatedAt(LocalDateTime.now());

        return classRepository.save(classroom);
    }

    @Override
    public List<ClassDTO> getClasses() {
        List<Class> classes = classRepository.findAll();

        return classes.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ClassDTO> getClassesByCourseId(int courseId) {
        List<Class> classes = classRepository.findByCourseId(courseId);

        return classes.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ClassDTO convertToDTO(Class classroom) {
        CourseDTO courseDTO = null;
        Course course = classroom.getCourse();
        LocalDateTime currentDate = LocalDateTime.now();

        if (course != null) {
            courseDTO = new CourseDTO(
                    course.getId(),
                    course.getCourseId(),
                    course.getName()
            );
        }

        ClassDTO classDTO = new ClassDTO();
        classDTO.setId(classroom.getId());
        classDTO.setClassId(classroom.getClassId());
        classDTO.setCourse(courseDTO);
        classDTO.setName(classroom.getName());
        classDTO.setStartDate(classroom.getStartDate());
        classDTO.setEndDate(classroom.getEndDate());
        classDTO.setExamDate(classroom.getExamDate());

        if (classroom.getEndDate().isBefore(currentDate)) {
            if (classroom.getStatus() != 2) {
                classDTO.setStatus((byte) 2);
                classroom.setStatus((byte) 2);
                classroom.setUpdatedAt(LocalDateTime.now());

                classRepository.save(classroom);
            }
        } else {
            classDTO.setStatus(classroom.getStatus());
        }

        classDTO.setCreatedAt(classroom.getCreatedAt());
        classDTO.setUpdatedAt(classroom.getUpdatedAt());

        return classDTO;
    }

    @Override
    public Class getClassByID(Integer id) {
        return classRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Class", "Id", id));
    }

    @Override
    public Class updateClass(Integer id, ClassDTO classDTO) {
        Class existingClass = classRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Class", "Id", id));

        existingClass.setClassId(classDTO.getClassId() != null ? classDTO.getClassId() : existingClass.getClassId());
        existingClass.setName(classDTO.getName() != null ? classDTO.getName() : existingClass.getName());
        existingClass.setStartDate(classDTO.getStartDate() != null ? classDTO.getStartDate() : existingClass.getStartDate());
        existingClass.setEndDate(classDTO.getEndDate() != null ? classDTO.getEndDate() : existingClass.getEndDate());
        existingClass.setExamDate(classDTO.getExamDate() != null ? classDTO.getExamDate() : existingClass.getExamDate());
        existingClass.setUpdatedAt(LocalDateTime.now());

        return classRepository.save(existingClass);
    }

    @Override
    public void deleteClassByID(Integer id) {
        classRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Course", "Id", id));

        classRepository.deleteById(id);
    }
}
