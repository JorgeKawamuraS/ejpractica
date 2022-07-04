package upc.edu.pe.ejpractica.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ejpractica.entities.Category;
import upc.edu.pe.ejpractica.exception.ResourceNotFoundException;
import upc.edu.pe.ejpractica.repositories.CategoryRepository;
import upc.edu.pe.ejpractica.services.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() throws Exception {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long aLong) throws Exception {
        return categoryRepository.findById(aLong)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",aLong));
    }

    @Override
    public Category update(Category entity, Long aLong) throws Exception {
        Category category = categoryRepository.findById(aLong)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",aLong));
        category.setName(entity.getName());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        Category category = categoryRepository.findById(aLong)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",aLong));
        categoryRepository.deleteById(aLong);
    }
}
