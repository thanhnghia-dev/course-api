package vn.edu.luphung.courseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.luphung.courseapi.model.Course;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

}
