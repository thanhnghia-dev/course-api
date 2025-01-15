package vn.edu.luphung.courseapi.service;

import vn.edu.luphung.courseapi.dto.ImageDTO;
import vn.edu.luphung.courseapi.model.Image;

import java.util.List;

public interface ImageService {
    List<ImageDTO> getImages();
    Image getImageByID(Integer id);
}
