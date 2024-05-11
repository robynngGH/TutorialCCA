package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.loan.model.Loan;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class LoanSpecification implements Specification<Loan> {

    private static final long serialVersionUID = 1L;
    private final SearchCriteria criteria;

    public LoanSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {
            Path<String> path = getPath(root);
            return builder.equal(path, criteria.getValue()); //this time only builder.equal is needed, since it's numbers and dates
        } else if (criteria.getOperation().equals("<=") && criteria.getValue() instanceof Date) {
            Path<Date> path = getDatePath(root);
            return builder.lessThanOrEqualTo(path, (Date) criteria.getValue());
        } else if (criteria.getOperation().equals(">=") && criteria.getValue() instanceof Date) {
            Path<Date> path = getDatePath(root);
            return builder.greaterThanOrEqualTo(path, (Date) criteria.getValue());
        }
        return null;
    }

    private Path<String> getPath(Root<Loan> root) {
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<String> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }

    //override for dates
    private Path<Date> getDatePath(Root<Loan> root) {
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<Date> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }
}
