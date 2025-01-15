package vn.edu.luphung.courseapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.luphung.courseapi.dto.LogDTO;
import vn.edu.luphung.courseapi.exception.ResourceNotFoundException;
import vn.edu.luphung.courseapi.model.Log;
import vn.edu.luphung.courseapi.model.User;
import vn.edu.luphung.courseapi.repository.LogRepository;
import vn.edu.luphung.courseapi.repository.UserRepository;
import vn.edu.luphung.courseapi.service.LogService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminServiceImpl adminService;

    @Override
    public Log saveLog(int userId, LogDTO logDTO) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "Id", logDTO.getId()));

        Log log = new Log();
        log.setUser(user);
        log.setLevel(logDTO.getLevel());
        log.setSource(logDTO.getSource());
        log.setIpAddress(logDTO.getIpAddress());
        log.setContent(logDTO.getContent());
        log.setStatus((byte) 1);
        log.setCreatedAt(LocalDateTime.now());

        return logRepository.save(log);
    }

    @Override
    public List<Log> getLogs() {
        return logRepository.findAll();
    }

    @Override
    public Log getLogByID(Integer id) {
        return logRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Log", "Id", id));
    }

    @Override
    public Log updateLogByID(Integer id, LogDTO logDTO) {
        Log existingLog = logRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Log", "Id", id));

        Log log = getLogByID(id);
        User user = adminService.getUserByID(Math.toIntExact(log.getUser().getId()));

        existingLog.setLevel(logDTO.getLevel() != null ? logDTO.getLevel() : existingLog.getLevel());
        existingLog.setUser(logDTO.getUser() != null ? user : existingLog.getUser());
        existingLog.setSource(logDTO.getSource() != null ? logDTO.getSource() : existingLog.getSource());
        existingLog.setIpAddress(logDTO.getIpAddress() != null ? logDTO.getIpAddress() : existingLog.getIpAddress());
        existingLog.setContent(logDTO.getContent() != null ? logDTO.getLevel() : existingLog.getContent());
        existingLog.setStatus(logDTO.getStatus() != 0 ? logDTO.getStatus() : existingLog.getStatus());

        return logRepository.save(existingLog);
    }

    @Override
    public void deleteLogByID(Integer id) {
        logRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Log", "Id", id));

        logRepository.deleteById(id);
    }
}
