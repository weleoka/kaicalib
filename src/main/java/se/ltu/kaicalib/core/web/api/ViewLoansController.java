package se.ltu.kaicalib.core.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.ltu.kaicalib.core.domain.entities.Loan;
import se.ltu.kaicalib.core.domain.LoanWrapper;
import se.ltu.kaicalib.core.domain.entities.Patron;
import se.ltu.kaicalib.core.repository.CopyRepository;
import se.ltu.kaicalib.core.repository.LoanRepository;
import se.ltu.kaicalib.core.repository.PatronRepository;
import se.ltu.kaicalib.core.repository.TitleRepository;

import java.util.List;

@Controller
@RequestMapping(path = "/patron/view_loans")
@SessionAttributes("loanWrapper")
// @Secured("PATRON")
public class ViewLoansController {

    private TitleRepository titleRepository;
    private PatronRepository patronRepository;
    private CopyRepository copyRepository;
    private LoanRepository loanRepository;
    private Long selId;

    @Autowired
    ViewLoansController(TitleRepository titleRepository,
                        PatronRepository patronRepository,
                        CopyRepository copyRepository, LoanRepository loanRepository) {
        this.titleRepository = titleRepository;
        this.patronRepository = patronRepository;
        this.copyRepository = copyRepository;
        this.loanRepository = loanRepository;
    }

    @ModelAttribute(name = "loans")
    public List<Loan> getLoans() {
        //TODO get current user instead of user with id 2
        Patron patron = patronRepository.getOne(2l);

        List<Loan> loans = patron.getLoans();

        return loans;
    }

    @ModelAttribute(name = "loanWrapper")
    public LoanWrapper makeForm() {
        LoanWrapper loan = new LoanWrapper();

        return loan;
    }

    @GetMapping
    public String viewLoans(@ModelAttribute("loans") List<Loan> loans, Model model) {

        model.addAttribute("loans", loans);

        return "core/loanView";
    }

    @PostMapping(path = "/cancel")
    public String cancelLoan(@ModelAttribute LoanWrapper loanWrapper) {

        /**
         * TODO this code doesn't work, but is never called since i disabled renew loan
         * TODO besides this code was originally written for loan deletion
         * the problem is that the wrapper object contains a null reference to a loan, and I can't manage to populate
         * the loanWrapper.loan field properly with the selected title from "loanView"
         */
        selId = loanWrapper.getLoan().getId();
        loanRepository.delete(loanRepository.getOne(selId));

        return "redirect:/patron/view_loans";
    }

}
