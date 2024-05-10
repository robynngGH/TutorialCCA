package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    /**
     * Method that returns a {@link Customer} by its ID
     *
     * @param id PK of entity
     * @return {@link Customer}
     */
    Customer get(Long id);

    /**
     * Method that finds all customers
     *
     * @return {@link List} of {@link Customer}
     */
    List<Customer> findAll();

    /**
     * Method that finds a customer by name
     * Used to detect conflicts with already existing customers
     *
     * @param name
     * @return {@link Customer}
     */
    Customer findByName(String name);

    /**
     * Method that saves updated customers or new ones
     *
     * @param id PK of entity
     * @param dto of customer
     */
    void save(Long id, CustomerDTO dto) throws Exception;

    /**
     * Method that deletes a customer
     *
     * @param id PK of entity
     * @throws Exception
     */
    void delete(Long id) throws Exception;
}
