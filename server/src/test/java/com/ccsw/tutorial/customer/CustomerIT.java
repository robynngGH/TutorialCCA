package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.CustomerDTO;
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
public class CustomerIT {
    //values to form up the URL
    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/customer";
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<List<CustomerDTO>> responseType = new ParameterizedTypeReference<List<CustomerDTO>>() {
    };

    /**
     * Listing all customers test
     */
    @Test
    public void findAllShouldReturnAllCustomers() {
        ResponseEntity<List<CustomerDTO>> responseEntity = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);
        assertNotNull(responseEntity);
        assertEquals(6, responseEntity.getBody().size());
        //initially there are 6 values in the database
    }

    public static final Long NEW_CUSTOMER_ID = 7L;
    public static final String NEW_CUSTOMER_NAME = "Customer 7";

    /**
     * Creating a new customer
     */
    @Test
    public void saveWithoutIdShouldCreateNewCustomer() {
        CustomerDTO dto = new CustomerDTO();
        dto.setName(NEW_CUSTOMER_NAME);
        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        ResponseEntity<List<CustomerDTO>> responseEntity = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);
        assertNotNull(responseEntity);
        assertEquals(7, responseEntity.getBody().size()); //checks that the size is now 7 instead of 6

        CustomerDTO customerSearch = responseEntity.getBody().stream().filter(item -> item.getId().equals(NEW_CUSTOMER_ID)).findFirst().orElse(null);
        assertNotNull(customerSearch);
        assertEquals(NEW_CUSTOMER_NAME, customerSearch.getName());
    }

    public static final String EXISTING_CUSTOMER_NAME = "Gabriella Bridgers";

    @Test
    public void saveWithExistingNameShouldThrowHttpConflict() {
        CustomerDTO dto = new CustomerDTO();
        dto.setName(EXISTING_CUSTOMER_NAME);
        ResponseEntity<?> responseEntity = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    public static final Long EXISTING_CUSTOMER_ID = 5L;

    /**
     * Updating the name of an existing customer
     */
    @Test
    public void modifyWithExistingIdShouldModifyCustomer() {
        CustomerDTO dto = new CustomerDTO();
        dto.setName(NEW_CUSTOMER_NAME);
        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + EXISTING_CUSTOMER_ID, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);
        //this time the URL goes to the customer ID path

        ResponseEntity<List<CustomerDTO>> responseEntity = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);
        assertNotNull(responseEntity);
        assertEquals(6, responseEntity.getBody().size()); //checks that the number of values is still 6

        CustomerDTO customerSearch = responseEntity.getBody().stream().filter(item -> item.getId().equals(EXISTING_CUSTOMER_ID)).findFirst().orElse(null);
        assertNotNull(customerSearch);
        assertEquals(NEW_CUSTOMER_NAME, customerSearch.getName());
    }

    /**
     * Returning internal server error when updating a non-existing customer
     */
    @Test
    public void modifyWithNonExistingIdShouldThrowInternalError() {
        CustomerDTO dto = new CustomerDTO();
        dto.setName(NEW_CUSTOMER_NAME);

        ResponseEntity<?> responseEntity = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + NEW_CUSTOMER_ID, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    /**
     * Deleting an existing customer
     */
    @Test
    public void deleteWithExistingIdShouldDeleteCustomer() {
        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + EXISTING_CUSTOMER_ID, HttpMethod.DELETE, null, Void.class);

        ResponseEntity<List<CustomerDTO>> responseEntity = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);
        assertNotNull(responseEntity);
        assertEquals(5, responseEntity.getBody().size()); //checks that the number of customers in the database is now 5
    }

    /**
     * Returning internal server error when deleting a non-existing customer
     */
    @Test
    public void deleteWithNonExistingIdShouldThrowInternalError() {
        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + NEW_CUSTOMER_ID, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
