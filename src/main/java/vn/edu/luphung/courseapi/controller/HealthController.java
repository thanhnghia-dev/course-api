package vn.edu.luphung.courseapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/public")
public class HealthController {

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}
