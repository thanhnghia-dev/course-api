package vn.edu.luphung.courseapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.luphung.courseapi.dto.StudentDTO;
import vn.edu.luphung.courseapi.model.Student;
import vn.edu.luphung.courseapi.service.StudentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/public/students")
public class StudentController {
    private final StudentService studentService;

    // Create a new Student
    @PostMapping()
    public ResponseEntity<?> createStudent(@RequestParam int classId,
                                           @ModelAttribute StudentDTO student) {

        try {
            if (studentService.isPhoneNumberExisted(student.getPhoneNumber())) {
                return new ResponseEntity<>("Số điện thoại đã tồn tại!",HttpStatus.CONFLICT);
            }

            if (studentService.isCitizenIdExisted(student.getCitizenId())) {
                return new ResponseEntity<>("Số CCCD đã tồn tại!",HttpStatus.CONFLICT);
            }

            Student savedStudent = studentService.saveStudent(classId, student);

            return new ResponseEntity<>(savedStudent,HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get all Student
    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getStudents();
    }

    // Get all Student
    @GetMapping("/by-class")
    public List<StudentDTO> getAllStudentsByClassId(@RequestParam("classId") int classId) {
        return studentService.getStudentsByClass(classId);
    }

    // Get Student by id
    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") int id) {
        return new ResponseEntity<>(studentService.getStudentByID(id), HttpStatus.OK);
    }

    // Update Student by id
    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudentById(@PathVariable("id") int id,
                                                     @RequestParam int classId,
                                                     @ModelAttribute StudentDTO studentDTO) {
        return new ResponseEntity<>(studentService.updateStudent(id, classId, studentDTO), HttpStatus.OK);
    }

    // Delete Student by id
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable("id") int id) {
        studentService.deleteStudentByID(id);
        return new ResponseEntity<>("Student " + id + " is deleted successfully!", HttpStatus.OK);
    }

}
