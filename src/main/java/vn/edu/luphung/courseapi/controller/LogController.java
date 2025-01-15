package vn.edu.luphung.courseapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.luphung.courseapi.dto.LogDTO;
import vn.edu.luphung.courseapi.model.Log;
import vn.edu.luphung.courseapi.service.LogService;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/logs")
public class LogController {
    @Autowired
    private LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    // Create a new Log
    @PostMapping()
    public ResponseEntity<Log> createLog(@RequestParam int userId,
                                         @ModelAttribute LogDTO log) {
        return new ResponseEntity<>(logService.saveLog(userId, log), HttpStatus.CREATED);
    }

    // Get all Log
    @GetMapping
    public List<Log> getAllLogs() {
        return logService.getLogs();
    }

    // Get Log by id
    @GetMapping("{id}")
    public ResponseEntity<Log> getLogById(@PathVariable ("id") int id) {
        return new ResponseEntity<>(logService.getLogByID(id), HttpStatus.OK);
    }

    // Update Log by id
    @PutMapping("{id}")
    public ResponseEntity<Log> updateLogById(@PathVariable ("id") int id,
                                                       @RequestBody LogDTO logDTO) {
        return new ResponseEntity<>(logService.updateLogByID(id, logDTO), HttpStatus.OK);
    }

    // Delete Log by id
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteLogById(@PathVariable ("id") int id) {
        logService.deleteLogByID(id);
        return new ResponseEntity<>("Log " + id + " is deleted successfully!", HttpStatus.OK);
    }

}
