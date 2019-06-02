package se.ltu.kaicalib.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.ltu.kaicalib.core.domain.entities.Loan;
import se.ltu.kaicalib.core.domain.entities.Patron;

import java.util.List;
import java.util.Optional;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT l FROM Loan l WHERE l.patron = :patron")
    List<Loan> findLoansByPatronId(@Param("patron") Patron patron);
}
