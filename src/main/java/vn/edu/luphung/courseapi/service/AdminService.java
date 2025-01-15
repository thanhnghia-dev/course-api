package vn.edu.luphung.courseapi.service;

import vn.edu.luphung.courseapi.dto.UserDTO;
import vn.edu.luphung.courseapi.model.User;

import java.util.List;

public interface AdminService {
    List<UserDTO> getUsers();
    List<UserDTO> getUserByRoleUser();
    User getUserByID(Integer id);
    User updateUser(Integer id, UserDTO userDTO);
    void deleteUserByID(Integer id);

}
