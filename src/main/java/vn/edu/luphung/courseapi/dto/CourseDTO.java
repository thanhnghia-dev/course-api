package vn.edu.luphung.courseapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CourseDTO {
    private Integer id;
    private String courseId;
    private String name;
    private byte status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CourseDTO() {
    }

    public CourseDTO(Integer id, String courseId, String name) {
        this.id = id;
        this.courseId = courseId;
        this.name = name;
    }
}
