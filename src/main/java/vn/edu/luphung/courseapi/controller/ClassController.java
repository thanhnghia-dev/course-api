package vn.edu.luphung.courseapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.luphung.courseapi.dto.ClassDTO;
import vn.edu.luphung.courseapi.model.Class;
import vn.edu.luphung.courseapi.service.ClassService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/public/classes")
public class ClassController {
    @Autowired
    private ClassService classService;

    // Create a new Class
    @PostMapping()
    public ResponseEntity<Class> createClass(@RequestParam int courseId,
                                             @ModelAttribute ClassDTO classDTO) {
        return new ResponseEntity<>(classService.saveClass(courseId, classDTO), HttpStatus.CREATED);
    }

    // Get all Class
    @GetMapping
    public List<ClassDTO> getAllClasses() {
        return classService.getClasses();
    }

    // Get Class by id
    @GetMapping("{id}")
    public ResponseEntity<Class> getClassById(@PathVariable ("id") int id) {
        return new ResponseEntity<>(classService.getClassByID(id), HttpStatus.OK);
    }

    // Update Class by id
    @PutMapping("{id}")
    public ResponseEntity<Class> updateClassById(@PathVariable ("id") int id,
                                                 @ModelAttribute ClassDTO classDTO) {
        return new ResponseEntity<>(classService.updateClass(id, classDTO), HttpStatus.OK);
    }

    // Delete Class by id
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteClassById(@PathVariable ("id") int id) {
        classService.deleteClassByID(id);
        return new ResponseEntity<>("Course " + id + " is deleted successfully!", HttpStatus.OK);
    }

}
