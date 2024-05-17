package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDTO;
import com.ccsw.tutorial.loan.model.LoanSearchDTO;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author rnavarro
 *
 */
public interface LoanService {

  /**
   * Finds a list of loans associated with a certain customer
   * Used to throw TwoGamesLoanedForCustomerShareDateException
   *
   * @param idCustomer
   * @return List of Loan
   */
  List<Loan> findLoansByCustomerId(Long idCustomer);

  /**
   * Finds a list of loans associated with a certain game
   * Used to throw SameGameLoanedShareDateException
   *
   * @param idGame
   * @return List of Loan
   */
  List<Loan> findLoansByGameId(Long idGame);

  Page<Loan> findPage(Long idGame, Long idCustomer, Timestamp date, LoanSearchDTO dto);

  void save(LoanDTO dto) throws RuntimeException;

  void delete(Long id) throws Exception;
}
