package vn.edu.luphung.courseapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "url", columnDefinition = "TEXT")
    private String url;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}