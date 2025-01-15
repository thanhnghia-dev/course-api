package vn.edu.luphung.courseapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "level")
    private String level;

    @Column(name = "source", columnDefinition = "TEXT")
    private String source;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "status")
    private byte status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
