package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDTO;

import java.util.List;

public interface CategoryService {
    /**
     * Recupera una {@link Category} a partir de su ID
     *
     * @param id PK de la entidad
     * @return {@link Category}
     */
    Category get(Long id);
    
    /**
     * Method that finds all categories
     *
     * @return {@link List} of {@link CategoryDTO}
     */
    List<Category> findAll();

    /**
     * Method that saves updated categories or new ones
     *
     * @param id PK of the entity
     * @param dto data of the entity
     */
    void save(Long id, CategoryDTO dto);

    /**
     * Method that deletes a category
     *
     * @param id PK of entity
     */
    void delete(Long id) throws Exception;
}
