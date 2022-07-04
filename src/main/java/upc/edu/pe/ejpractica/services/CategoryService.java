package upc.edu.pe.ejpractica.services;

import upc.edu.pe.ejpractica.entities.Category;

public interface CategoryService extends CrudService<Category,Long> {
    Category createCategory(Category category);
}
