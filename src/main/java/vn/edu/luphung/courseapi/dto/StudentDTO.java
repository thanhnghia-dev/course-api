package vn.edu.luphung.courseapi.dto;

import lombok.Getter;
import lombok.Setter;
import vn.edu.luphung.courseapi.model.Course;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudentDTO {
    private Integer id;
    private String studentId;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phoneNumber;
    private LocalDateTime dob;
    private String birthPlace;
    private byte gender;
    private String citizenId;
    private ClassDTO classroom;
    private String note;
    private byte status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
