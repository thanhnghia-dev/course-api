package vn.edu.luphung.courseapi.service;

import vn.edu.luphung.courseapi.dto.StudentDTO;
import vn.edu.luphung.courseapi.model.Student;

import java.util.List;

public interface StudentService {
    Student saveStudent(int classId, StudentDTO studentDTO);
    List<StudentDTO> getStudents();
    List<StudentDTO> getStudentsByClass(int classId);
    boolean isPhoneNumberExisted(String phoneNumber);
    Student getStudentByID(Integer id);
    Student updateStudent(Integer id, int classId, StudentDTO studentDTO);
    void deleteStudentByID(Integer id);

}
