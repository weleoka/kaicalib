package se.ltu.kaicalib.core.web.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.ltu.kaicalib.account.domain.User;
import se.ltu.kaicalib.account.web.PatronController;
import se.ltu.kaicalib.core.domain.Loan;
import se.ltu.kaicalib.core.domain.Patron;
import se.ltu.kaicalib.core.repository.CopyRepository;
import se.ltu.kaicalib.core.repository.LoanRepository;
import se.ltu.kaicalib.core.repository.PatronRepository;
import se.ltu.kaicalib.core.repository.TitleRepository;

import java.util.List;


/**
 *
 */
@Controller
@Secured("PATRON") // todo production check
public class LoanController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private TitleRepository titleRepository;
    private PatronRepository patronRepository;
    private CopyRepository copyRepository;
    private LoanRepository loanRepository;
    private PatronController patronController;

    @Autowired
    LoanController(TitleRepository titleRepository,
                        PatronRepository patronRepository,
                        CopyRepository copyRepository,
                        LoanRepository loanRepository,
                        PatronController patronController) {
        this.titleRepository = titleRepository;
        this.patronRepository = patronRepository;
        this.copyRepository = copyRepository;
        this.loanRepository = loanRepository;
        this.patronController = patronController;
    }

    @GetMapping("/borrow")
    public String borrowSelected(@RequestParam("id") Long id, Model model) {
        //todo work

        // todo Possibly create a flash object or something?
        String msg = String.format("The item (id %s) was successfully added to checkout list", id);

        model.addAttribute("success", msg);

        return "redirect:search";
    }


    @GetMapping("/view_loans")
    public String viewLoans() {
        // todo work
        return "/core/view_loans";
    }


    @GetMapping("/checkout")
    public String viewCheckout() {
        // todo work
        return "/core/view_checkout";
    }


    public List<Loan> getLoans() {
        //todo work
        User user = patronController.getAuthUser();
        Patron patron = patronRepository.getPatronByUserUuid(user.getUuid());
        List<Loan> loans = patron.getLoans();

        return loans;
    }

}
