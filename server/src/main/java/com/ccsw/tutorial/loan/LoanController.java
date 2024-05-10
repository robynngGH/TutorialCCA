package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDTO;
import com.ccsw.tutorial.loan.model.LoanSearchDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author rnavarro
 *
 */
@Tag(name = "Loan", description = "API of Loan")
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    LoanService loanService;
    @Autowired
    ModelMapper mapper;

    /**
     * Method that returns a filtered page list of {@link com.ccsw.tutorial.loan.model.Loan}
     *
     * @param idGame PK of the game
     * @param idCustomer PK of the customer
     * @param date to filter
     * @param dto of a Loan search
     * @return {@link Page} of {@link LoanDTO}
     */
    @Operation(summary = "Find page", description = "Method that returns a filtered page of loans")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<LoanDTO> findPage(@RequestParam(value = "idGame", required = false) Long idGame, @RequestParam(value = "idCustomer", required = false) Long idCustomer, @RequestParam(value = "date", required = false) Date date,
            @RequestBody LoanSearchDTO dto) {
        Page<Loan> page = this.loanService.findPage(idGame, idCustomer, date, dto);

        return new PageImpl<>(page.getContent().stream().map(e -> mapper.map(e, LoanDTO.class)).collect(Collectors.toList()), page.getPageable(), page.getTotalElements());
    }

    /**
     * Method that creates a new {@link com.ccsw.tutorial.loan.model.Loan}
     *
     * @param dto of entity
     */
    @Operation(summary = "Save", description = "Method that saves a new loan")
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public void save(@RequestBody LoanDTO dto) {
        this.loanService.save(dto);
    }

    /**
     * Method that deletes an existing {@link com.ccsw.tutorial.loan.model.Loan},
     * otherwise throws Exception
     *
     * @param id PK of entity
     * @throws Exception
     */
    @Operation(summary = "Delete", description = "Method that deletes a loan")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {
        this.loanService.delete(id);
    }
}
