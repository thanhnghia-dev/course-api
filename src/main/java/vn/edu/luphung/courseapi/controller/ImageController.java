package vn.edu.luphung.courseapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.luphung.courseapi.dto.ImageDTO;
import vn.edu.luphung.courseapi.model.Image;
import vn.edu.luphung.courseapi.service.ImageService;

import java.util.List;

@RestController
@RequestMapping("api/v1/public/images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    // Get all Image
    @GetMapping
    public List<ImageDTO> getAllImages() {
        return imageService.getImages();
    }

    // Get Image by id
    @GetMapping("{id}")
    public ResponseEntity<Image> getImageById(@PathVariable ("id") int id) {
        return new ResponseEntity<>(imageService.getImageByID(id), HttpStatus.OK);
    }

}
