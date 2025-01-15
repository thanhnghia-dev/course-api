package vn.edu.luphung.courseapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LogDTO {
    private Integer id;
    private UserDTO user;
    private String level;
    private String source;
    private String ipAddress;
    private String content;
    private byte status;
    private LocalDateTime createdAt;
}
