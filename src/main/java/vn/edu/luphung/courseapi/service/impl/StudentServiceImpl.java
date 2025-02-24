package vn.edu.luphung.courseapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.luphung.courseapi.dto.ClassDTO;
import vn.edu.luphung.courseapi.dto.StudentDTO;
import vn.edu.luphung.courseapi.exception.ResourceNotFoundException;
import vn.edu.luphung.courseapi.model.Class;
import vn.edu.luphung.courseapi.model.Student;
import vn.edu.luphung.courseapi.repository.ClassRepository;
import vn.edu.luphung.courseapi.repository.StudentRepository;
import vn.edu.luphung.courseapi.service.StudentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassRepository classRepository;

    @Override
    public Student saveStudent(int classId, StudentDTO studentDTO) {
        Class classroom = classRepository.findById(classId).orElseThrow(() ->
                new ResourceNotFoundException("Class", "Id", studentDTO.getClassroom().getId()));

        Student student = new Student();
        student.setStudentId(generateRandomStudentId());
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setDob(studentDTO.getDob());
        student.setBirthPlace(studentDTO.getBirthPlace());
        student.setGender(studentDTO.getGender());
        student.setClassroom(classroom);
        student.setNote(studentDTO.getNote());
        student.setStatus((byte) 1);
        student.setCreatedAt(LocalDateTime.now());

        return studentRepository.save(student);
    }

    public boolean isPhoneNumberExisted(String phoneNumber) {
        return studentRepository.existsByPhoneNumber(phoneNumber);
    }

    private String generateRandomStudentId() {
        Random ran = new Random();
        int generatedNum = ran.nextInt(100000);
        return String.format("%05d", generatedNum);
    }

    @Override
    public List<StudentDTO> getStudents() {
        List<Student> students = studentRepository.findAll();

        return students.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getStudentsByClass(int classId) {
        List<Student> students = studentRepository.findByClassroomId(classId);

        updateStudentStatus(students);

        return students.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private StudentDTO convertToDTO(Student student) {
        ClassDTO classDTO = null;
        Class classroom = student.getClassroom();

        if (classroom != null) {
            classDTO = new ClassDTO(
                    classroom.getId(),
                    classroom.getClassId(),
                    classroom.getName(),
                    classroom.getStartDate(),
                    classroom.getEndDate(),
                    classroom.getExamDate()
            );
        }

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setStudentId(student.getStudentId());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setFullName(student.getLastName() + " " + student.getFirstName());
        studentDTO.setPhoneNumber(student.getPhoneNumber());
        studentDTO.setDob(student.getDob());
        studentDTO.setBirthPlace(student.getBirthPlace());
        studentDTO.setGender(student.getGender());
        studentDTO.setClassroom(classDTO);
        studentDTO.setNote(student.getNote());
        studentDTO.setStatus(student.getStatus());
        studentDTO.setCreatedAt(student.getCreatedAt());
        studentDTO.setUpdatedAt(student.getUpdatedAt());

        return studentDTO;
    }

    public void updateStudentStatus(List<Student> students) {
        LocalDateTime currentDate = LocalDateTime.now();

        for (Student student : students) {
            Class classroom = student.getClassroom();
            if (classroom != null && classroom.getExamDate() != null) {
                if (student.getStatus() == 3) {
                    continue;
                }

                if (classroom.getExamDate().isBefore(currentDate) && student.getStatus() != 2) {
                    student.setStatus((byte) 2);
                    student.setUpdatedAt(currentDate);
                    studentRepository.save(student);
                }
            }
        }
    }

    @Override
    public Student getStudentByID(Integer id) {
        return studentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Student", "Id", id));
    }

    @Override
    public Student updateStudent(Integer id, int classId, StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Student", "Id", id));

        Class classroom = classRepository.findById(classId).orElseThrow(() ->
                    new ResourceNotFoundException("Class", "Id", classId));

        existingStudent.setClassroom(classroom);

        existingStudent.setStudentId(studentDTO.getStudentId() != null ? studentDTO.getStudentId() : existingStudent.getStudentId());
        existingStudent.setFirstName(studentDTO.getFirstName() != null ? studentDTO.getFirstName() : existingStudent.getFirstName());
        existingStudent.setLastName(studentDTO.getLastName() != null ? studentDTO.getLastName() : existingStudent.getLastName());
        existingStudent.setPhoneNumber(studentDTO.getPhoneNumber() != null ? studentDTO.getPhoneNumber() : existingStudent.getPhoneNumber());
        existingStudent.setDob(studentDTO.getDob() != null ? studentDTO.getDob() : existingStudent.getDob());
        existingStudent.setBirthPlace(studentDTO.getBirthPlace() != null ? studentDTO.getBirthPlace() : existingStudent.getBirthPlace());
        existingStudent.setGender(studentDTO.getGender() != 3 ? studentDTO.getGender() : existingStudent.getGender());
        existingStudent.setNote(studentDTO.getNote() != null ? studentDTO.getNote() : existingStudent.getNote());
        existingStudent.setStatus(studentDTO.getStatus() != 0 ? studentDTO.getStatus() : existingStudent.getStatus());
        existingStudent.setUpdatedAt(LocalDateTime.now());

        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudentByID(Integer id) {
        studentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Student", "Id", id));

        studentRepository.deleteById(id);
    }

}
