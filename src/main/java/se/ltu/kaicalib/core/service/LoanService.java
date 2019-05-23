package se.ltu.kaicalib.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ltu.kaicalib.core.domain.entities.Copy;
import se.ltu.kaicalib.core.domain.entities.Loan;
import se.ltu.kaicalib.core.domain.entities.Patron;
import se.ltu.kaicalib.core.repository.CopyRepository;
import se.ltu.kaicalib.core.repository.LoanRepository;

import java.util.List;

@Transactional(value = "coreTransactionManager")
@Service
public class LoanService {

    private PatronService patronService;
    private LoanRepository loanRepository;
    private CopyRepository copyRepository;


    @Autowired
    public LoanService(
        PatronService patronService,
        LoanRepository loanRepository,
        CopyRepository copyRepository) {
        this.patronService = patronService;
        this.loanRepository = loanRepository;
        this.copyRepository = copyRepository;
    }


    /**
     * Gets all the loans belonging to a certain Patron.
     *
     * Superfluous method present for readability reasons.
     *
     * @return
     */
    public List<Loan> getAllLoansForPatron() {
        Patron patron = patronService.getPatronForAuthUser();

        return patron.getLoans();
    }


    /**
     * Gets the relevant Loan object from db and then checks business rules
     * to see if it can be renewed. If it can then it returns the Loan object.
     *
     * todo If failing criteria then return message/businessRuleViolationCode
     * todo It would be good to and have a way of marking historic loan objects - like setting as renewed/returned.
     *  - alternatively by holding multiple loan periods within a loan object.
     *  - alternatively by having a renewCount incrementer for renewals and just a startDate. Arithmetic solution
     *
     * @param loanId
     * @return
     */
    public Loan renewLoan(Long loanId) {
        Loan loan = loanRepository.getOne(loanId);
        // if patron has no debt
        // if loan has not been renewed too many times
        // if copy is not reserved
        // if the current loan period is at least half-way through (stop instant renewals)
        Loan renewedLoan = new Loan(loan);
        loanRepository.delete(loan);
        // The following save vs saveAndFlush can be important later. https://stackoverflow.com/a/43884321/3092830
        //loanRepository.saveAndFlush(renewedLoan);
        loanRepository.save(renewedLoan); // todo Test if this is available in time for re-populating view

        return loan;
    }


    /**
     * Finds the available copies for a title and then proceeds with other business rules checks.
     *
     * It is possible that a list of available copies should be shown to the user and they choose
     * from it it personally, aspects such as geographical location can then be more easily dealt with.
     *
     * The method finishes by making a seletion of which copy will be returned for the new loan.
     *
     * This is method requires only a title ID.
     *
     * @param titleId
     * @return
     */
    public Copy getCopyForNewLoan(Long titleId) {
        // if patron has no debt
        // if the maximum loan per year is reached
        // if the copy is still available!
        List<Copy> availablecopies = copyRepository.findAllAvailableCopiesByTitleId(titleId);

        return availablecopies.get(1);
    }
}
