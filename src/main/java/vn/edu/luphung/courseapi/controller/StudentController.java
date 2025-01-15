package vn.edu.luphung.courseapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private StudentService studentService;

    // Create a new Student
    @PostMapping()
    public ResponseEntity<Student> createStudent(@RequestParam int classId,
                                                 @ModelAttribute StudentDTO student) {
        return new ResponseEntity<>(studentService.saveStudent(classId, student), HttpStatus.CREATED);
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
    public ResponseEntity<Student> getStudentById(@PathVariable ("id") int id) {
        return new ResponseEntity<>(studentService.getStudentByID(id), HttpStatus.OK);
    }

    // Update Student by id
    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudentById(@PathVariable ("id") int id,
                                                     @RequestParam int classId,
                                                     @ModelAttribute StudentDTO studentDTO) {
        return new ResponseEntity<>(studentService.updateStudent(id, classId, studentDTO), HttpStatus.OK);
    }

    // Delete Student by id
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable ("id") int id) {
        studentService.deleteStudentByID(id);
        return new ResponseEntity<>("Student " + id + " is deleted successfully!", HttpStatus.OK);
    }

}
