package upc.edu.pe.ejpractica.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ejpractica.entities.Category;
import upc.edu.pe.ejpractica.entities.Course;
import upc.edu.pe.ejpractica.exception.ResourceNotFoundException;
import upc.edu.pe.ejpractica.repositories.CategoryRepository;
import upc.edu.pe.ejpractica.repositories.CourseRepository;
import upc.edu.pe.ejpractica.services.CourseService;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course createCourse(Long categoryId, Course course) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
        course.setCategory(category);
        return courseRepository.save(course);
    }

    @Override
    public List<Course> findAll() throws Exception {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(Long aLong) throws Exception {
        return courseRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException("Course","Id",aLong));
    }

    @Override
    public Course update(Course entity, Long aLong) throws Exception {
        Course course = courseRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException("Course","Id",aLong));

        course.setDescription(entity.getDescription())
                .setName(entity.getName());
        return courseRepository.save(course);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        Course course = courseRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException("Course","Id",aLong));
        courseRepository.deleteById(aLong);

    }
}
