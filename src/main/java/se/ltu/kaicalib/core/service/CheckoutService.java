package se.ltu.kaicalib.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ltu.kaicalib.core.domain.CheckoutList;
import se.ltu.kaicalib.core.domain.entities.Copy;
import se.ltu.kaicalib.core.domain.entities.Loan;
import se.ltu.kaicalib.core.domain.entities.Patron;
import se.ltu.kaicalib.core.domain.entities.Receipt;

import java.util.List;


/**
 * Service that provides an easy junction between
 * loan and copy for especially managing the creation of new loans.
 *
 * It also can act as an intermediate in the creation of receipts. Although
 * currently no pre-processing of values passed set for Receipt takes place.
 *
 *
 */
@Transactional(value = "coreTransactionManager")
@Service
public class CheckoutService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private LoanService loanService;
    private CopyService copyService;


    public CheckoutService(
        LoanService loanService,
        CopyService copyService) {
        this.loanService = loanService;
        this.copyService = copyService;
    }


    /**
     * Processes the CheckoutList object and creates
     * new loans on copies, or renews old loans accordingly.
     *
     * todo Consider the pros and cons of Autowiring the checkoutList versus passing as argument to here.
     * todo As mentioned in LoanController this should not have to fetch objects from persistence again.
     *
     * @param checkoutList
     */
    @Transactional(value = "coreTransactionManager", readOnly = false)
    public Receipt checkOut(CheckoutList checkoutList, Patron patron) {
        List<Copy> copies = copyService.getAllCopiesByIdList(checkoutList.getCopiesIds());
        List<Loan> loans = loanService.getAllLoansByIdList(checkoutList.getLoansIds());
        Receipt receipt = new Receipt(patron);

        for (Copy copy : copies) {
            Loan createdLoan = loanService.createNewLoan(copy, patron);
            receipt.addLoan(createdLoan);
        }

        for (Loan loan : loans) {
            Loan renewedLoan = loanService.renewLoan(loan);
            receipt.addRenewedLoan(renewedLoan);
        }

        return receipt;
    }
}
