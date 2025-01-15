package vn.edu.luphung.courseapi.service;

import vn.edu.luphung.courseapi.dto.LogDTO;
import vn.edu.luphung.courseapi.model.Log;

import java.util.List;

public interface LogService {
    Log saveLog(int userId, LogDTO logDTO);
    List<Log> getLogs();
    Log getLogByID(Integer id);
    Log updateLogByID(Integer id, LogDTO logDTO);
    void deleteLogByID(Integer id);

}
