package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    /**
     * Listing customers test
     */
    @Test
    public void findAllShouldReturnAllCustomers() {
        List<Customer> list = new ArrayList<>();
        list.add(mock(Customer.class));

        when(customerRepository.findAll()).thenReturn(list);

        List<Customer> customers = customerService.findAll();
        assertNotNull(customers);
        assertEquals(1, customers.size());
    }

    public static final String EXISTING_CUSTOMER_NAME = "Gabriella Bridgers";

    /**
     * Finding a customer with an existing name
     */
    @Test
    public void findByNameWithExistingNameShouldReturnCustomer() {
        Customer customer = new Customer();
        customer.setName(EXISTING_CUSTOMER_NAME);

        when(customerRepository.findByName(EXISTING_CUSTOMER_NAME)).thenReturn(customer);

        Customer result = customerService.findByName(EXISTING_CUSTOMER_NAME);

        assertNotNull(customer);
        assertEquals(EXISTING_CUSTOMER_NAME, result.getName());
    }

    public static final String NEW_CUSTOMER_NAME = "Customer 7";

    /**
     * Creating a new customer
     * @throws Exception
     */
    @Test
    public void saveCustomerWithoutIdShouldInsert() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(NEW_CUSTOMER_NAME);

        ArgumentCaptor<Customer> customer = ArgumentCaptor.forClass(Customer.class);
        customerService.save(null, customerDTO);
        verify(customerRepository).save(customer.capture());

        assertEquals(NEW_CUSTOMER_NAME, customer.getValue().getName());
    }

    public static final Long EXISTING_CUSTOMER_ID = 3L;

    /**
     * Updating an existing customer by its ID
     * @throws Exception
     */
    @Test
    public void saveCustomerWithExistingIdShouldUpdate() throws Exception {
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setName(NEW_CUSTOMER_NAME);

        Customer customer = mock(Customer.class);
        when(customerRepository.findById(EXISTING_CUSTOMER_ID)).thenReturn(Optional.of(customer));
        customerService.save(EXISTING_CUSTOMER_ID, customerDto);

        verify(customerRepository).save(customer);
    }

    /**
     * Deleting existing customer
     * @throws Exception
     */
    @Test
    public void deleteExistingCustomerIdShouldDelete() throws Exception {
        Customer customer = mock(Customer.class);
        when(customerRepository.findById(EXISTING_CUSTOMER_ID)).thenReturn(Optional.of(customer));

        customerService.delete(EXISTING_CUSTOMER_ID);

        verify(customerRepository).deleteById(EXISTING_CUSTOMER_ID);
    }
}
