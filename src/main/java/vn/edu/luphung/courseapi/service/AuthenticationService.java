package vn.edu.luphung.courseapi.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import vn.edu.luphung.courseapi.dto.auth.AuthenticationResponse;
import vn.edu.luphung.courseapi.dto.UserDTO;
import vn.edu.luphung.courseapi.model.User;

public interface AuthenticationService {
    AuthenticationResponse register(UserDTO userDTO);
    AuthenticationResponse authenticate(User request);
    boolean isUsernameExist(String username);
    int getCurrentUserId();
    ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response);
}
