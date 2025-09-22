package vn.edu.luphung.courseapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.luphung.courseapi.dto.UserDTO;
import vn.edu.luphung.courseapi.model.User;
import vn.edu.luphung.courseapi.service.UserService;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/public/users")
public class UserController {
    @Autowired
    private UserService userService;

    // Get User info
    @GetMapping("/user-info")
    public ResponseEntity<User> getUserInformation() {
        return new ResponseEntity<>(userService.getUserInfo(), HttpStatus.OK);
    }

    // Update profile
    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(
            @ModelAttribute UserDTO userDTO,
            @RequestParam(value = "avatar", required = false) MultipartFile imageFile) throws IOException {
        try {
            User updatedUser = userService.updateUser(userDTO, imageFile);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (MaxUploadSizeExceededException ex) {
            return new ResponseEntity<>("File size exceeds the maximum limit of 10MB!", HttpStatus.PAYLOAD_TOO_LARGE);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Change password
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@ModelAttribute UserDTO userDTO) {
        userService.updatePassword(userDTO);
        return new ResponseEntity<>("User is changed password successfully!", HttpStatus.OK);
    }

    // Reset password
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String username,
                                                @ModelAttribute UserDTO userDTO) {
        userService.updatePasswordByUsername(username, userDTO);
        return new ResponseEntity<>("User is changed password successfully!", HttpStatus.OK);
    }

    // Check exist username
    @GetMapping("/check-exist")
    public ResponseEntity<Boolean> checkExistUsername(@RequestParam("username") String username) {
        boolean exists = userService.checkUsername(username);
        if (!exists) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
