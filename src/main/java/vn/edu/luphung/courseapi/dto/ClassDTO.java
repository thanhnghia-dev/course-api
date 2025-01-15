package vn.edu.luphung.courseapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClassDTO {
    private Integer id;
    private String classId;
    private CourseDTO course;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime examDate;

    private byte status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ClassDTO() {
    }

    public ClassDTO(Integer id, String classId, String name) {
        this.id = id;
        this.classId = classId;
        this.name = name;
    }

    public ClassDTO(Integer id, String classId, String name, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime examDate) {
        this.id = id;
        this.classId = classId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.examDate = examDate;
    }
}
