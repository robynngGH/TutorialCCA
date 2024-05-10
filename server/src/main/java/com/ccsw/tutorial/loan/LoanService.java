package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDTO;
import com.ccsw.tutorial.loan.model.LoanSearchDTO;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * @author rnavarro
 *
 */
public interface LoanService {

    Page<Loan> findPage(Long idGame, Long idCustomer, Date date, LoanSearchDTO dto);

    void save(LoanDTO dto);

    void delete(Long id) throws Exception;
}
