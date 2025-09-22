package vn.edu.luphung.courseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.luphung.courseapi.model.Class;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {
    List<Class> findByCourseId(int courseId);
}
