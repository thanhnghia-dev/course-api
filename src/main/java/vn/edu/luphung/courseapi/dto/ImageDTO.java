package vn.edu.luphung.courseapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageDTO {
    private Integer id;
    private String name;
    private String url;
    private UserDTO userDTO;

    public ImageDTO() {
    }

    public ImageDTO(Integer id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
}

