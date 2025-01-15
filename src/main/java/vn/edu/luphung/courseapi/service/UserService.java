package vn.edu.luphung.courseapi.service;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.luphung.courseapi.dto.UserDTO;
import vn.edu.luphung.courseapi.model.User;

import java.io.IOException;

public interface UserService {
    User getUserInfo();
    User updateUser(UserDTO userDTO, MultipartFile imageFile) throws IOException;
    void updatePassword(UserDTO userDTO);
    void updatePasswordByUsername(String username, UserDTO userDTO);
    boolean checkUsername(String username);
}
