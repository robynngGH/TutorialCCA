package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Category", description = "API of Category")
@RequestMapping(value = "/category")
@RestController
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    ModelMapper mapper;

    /**
     * Method that finds all categories
     *
     * @return {@link List} of {@link CategoryDTO}
     */
    @Operation(summary = "Find", description = "Method that returns a list of Categories")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<CategoryDTO> findAll() {
        List<Category> categories = this.categoryService.findAll();
        return categories.stream().map(e -> mapper.map(e, CategoryDTO.class)).collect(Collectors.toList());
    }

    /**
     * Method that saves updated categories or new ones
     *
     * @param id PK of the entity
     * @param dto data of the entity
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Category")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody CategoryDTO dto) {
        this.categoryService.save(id, dto);
    }

    /**
     * Method that deletes a category
     *
     * @param id PK of entity
     */
    @Operation(summary = "Delete", description = "Method that deletes a Category")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {
        this.categoryService.delete(id);
    }
}
