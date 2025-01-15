package vn.edu.luphung.courseapi.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.luphung.courseapi.dto.UserDTO;
import vn.edu.luphung.courseapi.exception.ResourceNotFoundException;
import vn.edu.luphung.courseapi.model.Image;
import vn.edu.luphung.courseapi.model.User;
import vn.edu.luphung.courseapi.repository.ImageRepository;
import vn.edu.luphung.courseapi.repository.UserRepository;
import vn.edu.luphung.courseapi.service.AuthenticationService;
import vn.edu.luphung.courseapi.service.UserService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    @Lazy
    private ImageServiceImpl imageService;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public User getUserInfo() {
        int userId = authenticationService.getCurrentUserId();
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "Id", userId));
    }

    @Override
    public User updateUser(UserDTO userDTO, MultipartFile imageFile) throws IOException {
        int userId = authenticationService.getCurrentUserId();
        User existingUser = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "Id", userId));

        existingUser.setFullName(userDTO.getFullName() != null ? userDTO.getFullName() : existingUser.getFullName());
        existingUser.setDob(userDTO.getDob() != null ? userDTO.getDob() : existingUser.getDob());
        existingUser.setGender(userDTO.getGender() != 0 ? userDTO.getGender() : existingUser.getGender());
        existingUser.setUpdatedAt(LocalDateTime.now());

        if (imageFile != null && !imageFile.isEmpty()) {
            if (imageFile.getSize() > 10 * 1024 * 1024) { // 10MB limit
                throw new IllegalStateException("File size exceeds the maximum limit of 10MB!");
            }

            Image existingImage = existingUser.getImage();
            if (existingImage != null) {
                String publicId = extractPublicId(existingUser.getImage().getUrl());
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

                Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(),
                        ObjectUtils.asMap("folder", "users"));

                existingImage.setName(existingUser.getFullName());
                existingImage.setUrl((String) uploadResult.get("url"));
                imageRepository.save(existingImage);
            } else {
                Image newImage = imageService.saveUserAvatar(imageFile, existingUser);
                existingUser.setImage(newImage);
            }
        }
        return userRepository.save(existingUser);
    }

    private String extractPublicId(String url) {
        if (url == null || !url.contains("/") || !url.contains(".")) {
            throw new IllegalArgumentException("Invalid URL format");
        }
        return url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('.'));
    }

    @Override
    public void updatePassword(UserDTO userDTO) {
        int userId = authenticationService.getCurrentUserId();
        User existingUser = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "Id", userId));

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        existingUser.setPassword(userDTO.getPassword() != null ? encodedPassword : existingUser.getPassword());
        existingUser.setUpdatedAt(LocalDateTime.now());

        userRepository.save(existingUser);
    }

    @Override
    public void updatePasswordByUsername(String username, UserDTO userDTO) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User", "username", username));

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        user.setPassword(encodedPassword);
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    @Override
    public boolean checkUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User", "username", username));

        return username.equals(user.getUsername());
    }

}
