package se.ltu.kaicalib.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.ltu.kaicalib.core.domain.entities.Loan;
import se.ltu.kaicalib.core.domain.entities.Patron;

import java.util.Optional;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    public Optional<Loan> findLoansByPatron(Patron patron);
}
