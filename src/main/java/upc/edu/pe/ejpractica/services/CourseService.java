package upc.edu.pe.ejpractica.services;

import upc.edu.pe.ejpractica.entities.Course;

public interface CourseService extends CrudService<Course,Long> {
    Course createCourse(Long categoryId, Course course);
}
