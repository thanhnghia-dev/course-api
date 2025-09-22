package vn.edu.luphung.courseapi.service;

import vn.edu.luphung.courseapi.dto.ClassDTO;
import vn.edu.luphung.courseapi.model.Class;
import vn.edu.luphung.courseapi.model.Course;

import java.util.List;

public interface ClassService {
    Class saveClass(int courseId, ClassDTO classDTO);
    List<ClassDTO> getClasses();
    List<ClassDTO> getClassesByCourseId(int courseId);
    Class getClassByID(Integer id);
    Class updateClass(Integer id, ClassDTO classDTO);
    void deleteClassByID(Integer id);

}
