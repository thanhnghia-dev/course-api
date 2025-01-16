package vn.edu.luphung.courseapi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SelfPingService {
    private static final Logger logger = LoggerFactory.getLogger(SelfPingService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 300000) // 5 minutes
    public void ping() {
        String appUrl = "https://course-api-io3d.onrender.com";
        try {
            restTemplate.getForObject(appUrl, String.class);
            logger.info("Self-ping successful to {}", appUrl);
        } catch (Exception e) {
            logger.error("Self-ping failed: {}", e.getMessage());
        }
    }
}
