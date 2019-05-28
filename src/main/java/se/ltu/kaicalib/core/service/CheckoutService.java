package se.ltu.kaicalib.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ltu.kaicalib.core.domain.CheckoutList;
import se.ltu.kaicalib.core.domain.entities.Copy;
import se.ltu.kaicalib.core.domain.entities.Loan;
import se.ltu.kaicalib.core.domain.entities.Patron;
import se.ltu.kaicalib.core.domain.entities.Receipt;

import java.util.List;


/**
 * Facilitates easy junction between loan and copy,
 * especially managing the creation of new loans.
 *
 * It also can act as an intermediate in the creation of receipts.
 * todo pre-processing of values passed for Receipt.
 *
 *
 * @author
 */
@Transactional(value = "coreTransactionManager")
@Service
public class CheckoutService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private LoanService loanService;
    private CopyService copyService;


    @Autowired
    public CheckoutService(LoanService loanService, CopyService copyService) {
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
     * todo is it better to Fetch all copies and loans at once or fetch them individually within their
     *  respective Service classes? Implemented but still needs testing and consideration again.
     *
     * @param checkoutList
     */
    public Receipt checkOut(CheckoutList checkoutList, Patron patron) {
        Receipt receipt = new Receipt(patron);

        for (Long copyId : checkoutList.getCopiesIds()) {
            Loan createdLoan = loanService.createNewLoan(copyId, patron);
            receipt.addLoan(createdLoan);
            checkoutList.removeCopyId(copyId);
        }

        for (Long loanId : checkoutList.getLoansIds()) {
            Loan renewedLoan = loanService.renewLoanByLoanId(loanId);
            receipt.addRenewedLoan(renewedLoan);
            checkoutList.removeLoanId(loanId);
        }
        logger.debug("Created {} loans for patron id: {}", receipt.gettotalLoanCount(), patron.getId());

        return receipt;
    }


    /**
     * Checks out a single copy for a patron.
     *
     * Mostly for testing purposes but may be useful.
     *
     * @param copyId
     * @param patron
     * @return
     */
    public Receipt checkOut(Long copyId, Patron patron) {
        Receipt receipt = new Receipt(patron);
        receipt.addLoan(loanService.createNewLoan(copyId, patron));

        return receipt;
    }
}
