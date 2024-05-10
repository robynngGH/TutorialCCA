package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    /**
     * Listing test
     */
    @Test
    public void findAllShouldReturnAllCategories() {
        List<Category> list = new ArrayList<>();
        list.add(mock(Category.class));

        when(categoryRepository.findAll()).thenReturn(list);

        List<Category> categories = categoryService.findAll();

        assertNotNull(categories);
        assertEquals(1, categories.size()); //expected size 1
    }

    public static final String CATEGORY_NAME = "CAT1";

    /**
     * Create test
     */
    @Test
    public void saveCategoryIdDoesNotExistShouldInsert() {
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setName(CATEGORY_NAME);

        ArgumentCaptor<Category> category = ArgumentCaptor.forClass(Category.class);

        categoryService.save(null, categoryDto);

        verify(categoryRepository).save(category.capture());

        assertEquals(CATEGORY_NAME, category.getValue().getName());
        //expected category to be created when the ID given is null
    }

    public static final Long CATEGORY_ID_EXISTS = 1L;

    /**
     * Update test when the ID exists
     */
    @Test
    public void saveCategoryIdExistsShouldUpdate() {

        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setName(CATEGORY_NAME);

        Category category = mock(Category.class);
        when(categoryRepository.findById(CATEGORY_ID_EXISTS)).thenReturn(Optional.of(category));

        categoryService.save(CATEGORY_ID_EXISTS, categoryDto);

        verify(categoryRepository).save(category);
    }

    /**
     * Delete test
     */
    @Test
    public void deleteExistsCategoryIdShouldDelete() throws Exception {
        Category category = mock(Category.class);
        when(categoryRepository.findById(CATEGORY_ID_EXISTS)).thenReturn(Optional.of(category));

        categoryService.delete(CATEGORY_ID_EXISTS);

        verify(categoryRepository).deleteById(CATEGORY_ID_EXISTS);
    }

    /**
     * Tests to get categories in GameServiceImpl
     */
    public static final Long NOT_EXISTS_CATEGORY_ID = 0L;

    @Test
    public void getExistsCategoryIdShouldReturnCategory() {

        Category category = mock(Category.class);
        when(category.getId()).thenReturn(CATEGORY_ID_EXISTS);
        when(categoryRepository.findById(CATEGORY_ID_EXISTS)).thenReturn(Optional.of(category));

        Category categoryResponse = categoryService.get(CATEGORY_ID_EXISTS);

        assertNotNull(categoryResponse);
        assertEquals(CATEGORY_ID_EXISTS, category.getId());
    }

    @Test
    public void getNotExistsCategoryIdShouldReturnNull() {

        when(categoryRepository.findById(NOT_EXISTS_CATEGORY_ID)).thenReturn(Optional.empty());

        Category category = categoryService.get(NOT_EXISTS_CATEGORY_ID);

        assertNull(category);
    }
}
