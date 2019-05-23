package se.ltu.kaicalib.core.web.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.ltu.kaicalib.core.domain.CheckoutList;
import se.ltu.kaicalib.core.domain.entities.Copy;
import se.ltu.kaicalib.core.domain.entities.Loan;
import se.ltu.kaicalib.core.service.LoanService;

import static se.ltu.kaicalib.core.utils.GenUtil.toArr;


/**
 * Routes controller specific for actions
 * concerning loans. These are restricted to the Role PATRON.
 *
 *
 */
@Controller
@Secured("PATRON") // todo production check
public class LoanController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private LoanService loanService;
    private CheckoutList checkoutList;
    private MessageSource messageSource;


    @Autowired
    LoanController(
        LoanService loanService,
        CheckoutList checkoutList,
        MessageSource messageSource) {
        this.loanService = loanService;
        this.checkoutList = checkoutList;
        this.messageSource = messageSource;
    }


    @GetMapping("/add_to_checkout")
    public String addTitleToCheckout(@RequestParam("id") Long titleId, Model model) {
        Copy copy = loanService.getCopyForNewLoan(titleId);
        checkoutList.addCopyIdToCheckout(copy.getId());

        // if all the above worked then do this, if it did not then fish the BusinessViolationCode out from the stack and process it.
        String msg = messageSource.getMessage("Copy.addedToCheckout.success", toArr(titleId.toString()), null);
        model.addAttribute("success", msg);

        return "redirect:search";
    }


    @GetMapping("/loan_renew")
    public String renewLoan(@RequestParam("id") Long id, Model model) {
        Loan loan = loanService.renewLoan(id);
        checkoutList.addLoanIdToCheckout(loan.getId());

        // if all the above worked then do this, if it did not then fish the BusinessViolationCode out from the stack and process it.
        String msg = messageSource.getMessage("Loan.renew.success", null, null);
        model.addAttribute("success", msg);

        return "redirect:view_loans";
    }


    @GetMapping("/view_loans")
    public String viewLoans(Model model) {
        model.addAttribute("loans", loanService.getAllLoansForPatron());

        return "/core/view_loans";
    }


    @GetMapping("/checkout")
    public String viewCheckout(Model model) {
            // copyIds to titles
            // and loanIds to Loans
        //model.addAttribute(titles);
        //model.addAttribute(loans);
        // todo work Consider if it wasn't better to keep objects in the Lists rather than Massive-fetching it all
        return "/core/view_checkout";
    }


    @PostMapping("/checkout")
    public String proceedWithCheckout(Model model) {
        // todo work
        return "/core/receipt";
    }
}


/*
    private PatronRepository patronRepository;
    private CopyRepository copyRepository;

        TitleRepository titleRepository,
        PatronRepository patronRepository,
        CopyRepository copyRepository,
            this.titleRepository = titleRepository;
            this.patronRepository = patronRepository;
            this.copyRepository = copyRepository;
 */
