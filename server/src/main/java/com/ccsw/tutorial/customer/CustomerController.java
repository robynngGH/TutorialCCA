package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Customer", description = "API of Customer")
@RequestMapping(value = "/customer")
@RestController
@CrossOrigin(origins = "*")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    ModelMapper mapper;

    /**
     * Method that finds all customers
     *
     * @return {@link List} of {@link CustomerDTO}
     */
    @Operation(summary = "Find", description = "Method that returns a list of customers")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<CustomerDTO> findAll() {
        List<Customer> customers = this.customerService.findAll();
        return customers.stream().map(e -> mapper.map(e, CustomerDTO.class)).collect(Collectors.toList());
    }

    /**
     * Method that saves updated customers or new ones
     *
     * @param id PK of entity
     * @param dto of customer
     */
    @Operation(summary = "Save or update", description = "Method that saves or updates a customer")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody CustomerDTO dto) throws Exception {
        this.customerService.save(id, dto);
    }

    @Operation(summary = "Delete", description = "Method that deletes an existing customer")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {
        this.customerService.delete(id);
    }
}
