package vn.edu.luphung.courseapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.luphung.courseapi.dto.UserDTO;
import vn.edu.luphung.courseapi.model.User;
import vn.edu.luphung.courseapi.service.AdminService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/admin/users")
public class AdminController {
    @Autowired
    private AdminService adminService;

    // Get all User
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return adminService.getUsers();
    }

    // Get all User by user role
    @GetMapping("/user-role")
    public List<UserDTO> getUsersByUserRole() {
        return adminService.getUserByRoleUser();
    }

    // Get User by id
    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        return new ResponseEntity<>(adminService.getUserByID(id), HttpStatus.OK);
    }

    // Update user
    @PutMapping("/update-user/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable ("id") int id,
                                               @ModelAttribute UserDTO userDTO) {
        return new ResponseEntity<>(adminService.updateUser(id, userDTO), HttpStatus.OK);
    }

    // Delete User by id
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable ("id") int id) {
        adminService.deleteUserByID(id);
        return new ResponseEntity<>("User " + id + " is deleted successfully!", HttpStatus.OK);
    }

}
