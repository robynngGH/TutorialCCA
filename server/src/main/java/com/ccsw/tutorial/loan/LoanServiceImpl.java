package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.common.custom_exceptions.MoreThan14DaysException;
import com.ccsw.tutorial.common.custom_exceptions.MoreThanTwoGamesLoanedForCustomerShareDateException;
import com.ccsw.tutorial.common.custom_exceptions.ReturnDatePriorToLoanDateException;
import com.ccsw.tutorial.common.custom_exceptions.SameGameLoanedShareDateException;
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

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

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
  public List<Loan> findLoansByCustomerId(Long idCustomer) {
    return this.loanRepository.findLoansByCustomerId(idCustomer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Loan> findLoansByGameId(Long idGame) {
    return this.loanRepository.findLoansByGameId(idGame);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Page<Loan> findPage(Long idGame, Long idCustomer, Timestamp date, LoanSearchDTO dto) {

    LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("game.id", ":", idGame));
    LoanSpecification customerSpec = new LoanSpecification(new SearchCriteria("customer.id", ":", idCustomer));
    LoanSpecification startDateSpec = new LoanSpecification(new SearchCriteria("start_date", "<=", date));
    LoanSpecification endDateSpec = new LoanSpecification(new SearchCriteria("end_date", ">=", date));

    Specification<Loan> spec = Specification.where(gameSpec).and(customerSpec).and(startDateSpec).and(endDateSpec);

    return this.loanRepository.findAll(spec, dto.getPageable().getPageable());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(LoanDTO dto) throws RuntimeException {
    long diffInMillies = Math.abs(dto.getEnd_date().getTime() - dto.getStart_date().getTime());
    long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    List<Loan> loansByGame = loanRepository.findLoansByGameId(dto.getGame().getId());

    //possible exceptions, in order:
    //1. return date being prior to the loan date
    //2. more than 14 days for the loan
    //3. game loan associated to more than one customer for a date range that overlaps
    //4. a customer having more than two games loaned in date ranges that overlap
    if (dto.getEnd_date().before(dto.getStart_date()))
      throw new ReturnDatePriorToLoanDateException("The return date cannot be prior to the loan date");
    if (diffInDays > 14)
      throw new MoreThan14DaysException("The allowed loan time cannot be higher than 14 days");
    if (loansByGame != null) {
      for (ListIterator<Loan> iterator = loansByGame.listIterator(); iterator.hasNext(); ) {
        Loan loan = iterator.next();
        Date loanStartDate = loan.getStart_date();
        Date loanEndDate = loan.getEnd_date();
        if (loanStartDate.equals(dto.getStart_date()) || loanEndDate.equals(dto.getEnd_date()) || loanStartDate.equals(dto.getEnd_date()) || loanEndDate.equals(dto.getStart_date()) || (loanStartDate.before(dto.getEnd_date())
            && loanEndDate.after(dto.getStart_date())))
          throw new SameGameLoanedShareDateException("This game is not available for this date range");
      }
    }

    List<Loan> loansByCustomer = loanRepository.findLoansByCustomerId(dto.getCustomer().getId());

    if (loansByCustomer != null) {
      int gameCountForCustomer = 0; //if it goes to 2, it will be more than the 2 allowed
      for (ListIterator<Loan> iterator = loansByCustomer.listIterator(); iterator.hasNext(); ) {
        Loan loan = iterator.next();
        Date loanStartDate = loan.getStart_date();
        Date loanEndDate = loan.getEnd_date();
        if (loanStartDate.equals(dto.getStart_date()) || loanEndDate.equals(dto.getEnd_date()) || loanStartDate.equals(dto.getEnd_date()) || loanEndDate.equals(dto.getStart_date()) || (loanStartDate.before(dto.getEnd_date())
            && loanEndDate.after(dto.getStart_date()))) {
          gameCountForCustomer++;
          if (gameCountForCustomer > 1)
            throw new MoreThanTwoGamesLoanedForCustomerShareDateException("This customer already has a game loaned for this date range");
        }
      }
    }

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
