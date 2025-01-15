package vn.edu.luphung.courseapi.dto;

import lombok.Getter;
import lombok.Setter;
import vn.edu.luphung.courseapi.model.enums.Role;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private Integer id;
    private String userId;
    private String fullName;
    private String username;
    private LocalDateTime dob;
    private byte gender;
    private String password;
    private Role role;
    private byte status;
    private ImageDTO image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDTO() {
    }

}

