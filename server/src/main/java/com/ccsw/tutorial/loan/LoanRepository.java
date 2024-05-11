package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author rnavarro
 *
 */
public interface LoanRepository extends CrudRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {
    @Override
    @EntityGraph(attributePaths = { "game", "customer" })
    Page<Loan> findAll(Specification<Loan> spec, Pageable pageable);

    @Query("SELECT l FROM Loan l WHERE l.game.id = ?1")
    List<Loan> findLoansByGameId(Long idGame);

    @Query("SELECT l FROM Loan l WHERE l.customer.id = ?1")
    List<Loan> findLoansByCustomerId(Long idCustomer);
}
