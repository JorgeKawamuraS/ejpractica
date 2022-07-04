package upc.edu.pe.ejpractica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ejpractica.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
}
