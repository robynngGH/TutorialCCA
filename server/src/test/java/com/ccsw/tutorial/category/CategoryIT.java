package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.CategoryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryIT {
    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/category";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<List<CategoryDTO>> responseType = new ParameterizedTypeReference<List<CategoryDTO>>() {
    };

    /**
     * Listing test
     */
    @Test
    public void findAllShouldReturnAllCategories() {
        ResponseEntity<List<CategoryDTO>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);
        assertNotNull(response);
        assertEquals(3, response.getBody().size()); //expected 3 results originally in the DB
    }

    public static final Long NEW_CATEGORY_ID = 4L;
    public static final String NEW_CATEGORY_NAME = "CAT4";

    /**
     * Create test
     */
    @Test
    public void saveWithoutIdShouldCreateNewCategory() {
        CategoryDTO dto = new CategoryDTO();
        dto.setName(NEW_CATEGORY_NAME);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        ResponseEntity<List<CategoryDTO>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);
        assertNotNull(response);
        assertEquals(4, response.getBody().size()); //checks again that there is now 4 items instead of 3

        CategoryDTO categorySearch = response.getBody().stream().filter(item -> item.getId().equals(NEW_CATEGORY_ID)).findFirst().orElse(null);
        assertNotNull(categorySearch);
        assertEquals(NEW_CATEGORY_NAME, categorySearch.getName());
    }

    /**
     * Update test when ID exists
     */
    public static final Long MODIFY_CATEGORY_ID = 3L;

    @Test
    public void modifyWithExistingIdShouldModifyCategory() {

        CategoryDTO dto = new CategoryDTO();
        dto.setName(NEW_CATEGORY_NAME);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + MODIFY_CATEGORY_ID, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);
        //modifies ID 3L (MODIFY_CATEGORY_ID)

        ResponseEntity<List<CategoryDTO>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);
        assertNotNull(response);
        assertEquals(3, response.getBody().size()); //this time it expects again the original size

        CategoryDTO categorySearch = response.getBody().stream().filter(item -> item.getId().equals(MODIFY_CATEGORY_ID)).findFirst().orElse(null);
        assertNotNull(categorySearch);
        assertEquals(NEW_CATEGORY_NAME, categorySearch.getName());
    }

    /**
     * Update test when ID does not exist
     */
    @Test
    public void modifyWithNotExistingIdShouldInternalError() {
        CategoryDTO dto = new CategoryDTO();
        dto.setName(NEW_CATEGORY_NAME);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + NEW_CATEGORY_ID, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);
        //NEW_CATEGORY_ID does not exist

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()); //expected internal server error
    }

    public static final Long DELETE_CATEGORY_ID = 2L;

    /**
     * Delete test
     */
    @Test
    public void deleteWithExistingIdShouldDeleteCategory() {
        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + DELETE_CATEGORY_ID, HttpMethod.DELETE, null, Void.class);

        ResponseEntity<List<CategoryDTO>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);
        assertNotNull(response);
        assertEquals(2, response.getBody().size()); //expected size of 2 since it deleted one category
    }

    /**
     * Delete test when ID given does not exist
     */
    @Test
    public void deleteWithNonExistingIdShouldInternalError() {
        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + NEW_CATEGORY_ID, HttpMethod.DELETE, null, Void.class);
        //NEW_CATEGORY_ID does not exist

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
