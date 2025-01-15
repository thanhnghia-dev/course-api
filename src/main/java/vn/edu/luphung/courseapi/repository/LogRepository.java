package vn.edu.luphung.courseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.luphung.courseapi.model.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {

}
