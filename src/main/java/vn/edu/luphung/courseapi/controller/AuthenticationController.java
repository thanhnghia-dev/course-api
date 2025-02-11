package vn.edu.luphung.courseapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.luphung.courseapi.dto.UserDTO;
import vn.edu.luphung.courseapi.dto.auth.AuthenticationResponse;
import vn.edu.luphung.courseapi.model.User;
import vn.edu.luphung.courseapi.repository.UserRepository;
import vn.edu.luphung.courseapi.service.AuthenticationService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping(value = "/register")
    public ResponseEntity<?> signUp(@ModelAttribute UserDTO user) {
        try {
            if (authenticationService.isUsernameExist(user.getUsername())) {
                return new ResponseEntity<>("Username đã tồn tại!", HttpStatus.BAD_REQUEST);
            }

            AuthenticationResponse response = authenticationService.register(user);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@ModelAttribute User user) {
        User dbUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(()->new RuntimeException("No user found"));

        if (dbUser.getStatus() == 0) {
            return new ResponseEntity<>("Tài khoản đã bị ngừng hoạt động!", HttpStatus.FORBIDDEN);
        }

        AuthenticationResponse response = authenticationService.authenticate(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authenticationService.refreshToken(request, response);
    }
}
