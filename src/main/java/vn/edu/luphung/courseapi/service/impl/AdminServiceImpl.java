package vn.edu.luphung.courseapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.luphung.courseapi.dto.ImageDTO;
import vn.edu.luphung.courseapi.dto.UserDTO;
import vn.edu.luphung.courseapi.exception.ResourceNotFoundException;
import vn.edu.luphung.courseapi.model.Image;
import vn.edu.luphung.courseapi.model.User;
import vn.edu.luphung.courseapi.model.enums.Role;
import vn.edu.luphung.courseapi.repository.UserRepository;
import vn.edu.luphung.courseapi.service.AdminService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getUserByRoleUser() {
        List<User> users = userRepository.findAll().stream()
                .filter(u -> u.getRole() != Role.ADMIN)
                .toList();

        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private UserDTO convertToDTO(User user) {
        ImageDTO imageDTO = null;
        Image image = user.getImage();

        if (image != null) {
            imageDTO = new ImageDTO(
                    image.getId(),
                    image.getName(),
                    image.getUrl()
            );
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserId(user.getUserId());
        userDTO.setFullName(user.getFullName());
        userDTO.setUsername(user.getUsername());
        userDTO.setDob(user.getDob());
        userDTO.setGender(user.getGender());
        userDTO.setRole(user.getRole());
        userDTO.setStatus(user.getStatus());
        userDTO.setImage(imageDTO);
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());

        return userDTO;
    }

    @Override
    public User getUserByID(Integer id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", "Id", id));
    }

    @Override
    public User updateUser(Integer id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", "Id", id));

        existingUser.setFullName(userDTO.getFullName() != null ? userDTO.getFullName() : existingUser.getFullName());
        existingUser.setUsername(userDTO.getUsername() != null ? userDTO.getUsername() : existingUser.getUsername());
        existingUser.setDob(userDTO.getDob() != null ? userDTO.getDob() : existingUser.getDob());
        existingUser.setGender(userDTO.getGender() != 0 ? userDTO.getGender() : existingUser.getGender());
        existingUser.setRole(userDTO.getRole() != null ? userDTO.getRole() : existingUser.getRole());
        existingUser.setStatus(userDTO.getStatus() != 0 ? userDTO.getStatus() : existingUser.getStatus());
        existingUser.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUserByID(Integer id) {
        userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", "Id", id));

        userRepository.deleteById(id);
    }

}
