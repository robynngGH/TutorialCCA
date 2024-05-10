package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer get(Long id) {
        return this.customerRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> findAll() {
        return (List<Customer>) this.customerRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer findByName(String name) {
        return this.customerRepository.findByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, CustomerDTO dto) throws Exception {
        Customer nameExists = customerRepository.findByName(dto.getName());
        //will not be null if the name in the DTO already exists
        if (nameExists != null)
            //throw new HttpClientErrorException(HttpStatus.CONFLICT, "The customer already exists");
            throw new Exception("The customer already exists");

        Customer customer;
        if (id == null)
            customer = new Customer();
        else
            customer = this.get(id);

        customer.setName(dto.getName());
        this.customerRepository.save(customer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {
        if (this.get(id) == null)
            throw new Exception("ID does not exist");

        this.customerRepository.deleteById(id);
    }
}
