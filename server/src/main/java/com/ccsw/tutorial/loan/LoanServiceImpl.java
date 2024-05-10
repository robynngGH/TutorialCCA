package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.customer.CustomerService;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDTO;
import com.ccsw.tutorial.loan.model.LoanSearchDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author rnavarro
 *
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    GameService gameService;
    @Autowired
    CustomerService customerService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> findPage(Long idGame, Long idCustomer, Date date, LoanSearchDTO dto) {

        LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("game.id", ":", idGame));
        LoanSpecification customerSpec = new LoanSpecification(new SearchCriteria("customer.id", ":", idCustomer));
        LoanSpecification dateSpec = new LoanSpecification(new SearchCriteria("date", "between", date));
        Specification<Loan> spec = Specification.where(gameSpec).and(customerSpec);

        return this.loanRepository.findAll(spec, dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(LoanDTO dto) {
        Loan loan = new Loan();

        BeanUtils.copyProperties(dto, loan, "id", "game", "customer");

        loan.setGame(gameService.get(dto.getGame().getId()));
        loan.setCustomer(customerService.get(dto.getCustomer().getId()));

        this.loanRepository.save(loan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.loanRepository.findById(id).orElse(null) == null)
            throw new Exception("ID does not exist");

        this.loanRepository.deleteById(id);
    }
}
