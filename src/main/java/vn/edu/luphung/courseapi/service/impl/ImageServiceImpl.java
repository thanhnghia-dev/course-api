package vn.edu.luphung.courseapi.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.luphung.courseapi.dto.ImageDTO;
import vn.edu.luphung.courseapi.exception.ResourceNotFoundException;
import vn.edu.luphung.courseapi.model.Image;
import vn.edu.luphung.courseapi.model.User;
import vn.edu.luphung.courseapi.repository.ImageRepository;
import vn.edu.luphung.courseapi.service.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private Cloudinary cloudinary;

    public Image saveUserAvatar(MultipartFile file, User user) throws IOException {
        String fullName = user.getFullName().replaceAll(" ", "_");
        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        String fileName = fullName + fileExtension;

        Path userDirectory = Paths.get("users");

        if (Files.notExists(userDirectory)) {
            Files.createDirectories(userDirectory);
        }

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("public_id", "users/" + fileName));

        String url = (String) uploadResult.get("url");

        Image image = new Image();
        image.setName(fullName);
        image.setUrl(url);
        image.setUser(user);

        return imageRepository.save(image);
    }

    @Override
    public List<ImageDTO> getImages() {
        List<Image> images = imageRepository.findAll();

        return images.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    private ImageDTO convertToDTO(Image image) {

        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(image.getId());
        imageDTO.setName(image.getName());
        imageDTO.setUrl(image.getUrl());

        return imageDTO;
    }

    @Override
    public Image getImageByID(Integer id) {
        return imageRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Image", "Id", id));
    }

}
