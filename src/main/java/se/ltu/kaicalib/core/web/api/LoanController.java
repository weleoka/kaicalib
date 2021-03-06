package se.ltu.kaicalib.core.web.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.ltu.kaicalib.core.domain.CheckoutList;
import se.ltu.kaicalib.core.domain.entities.Copy;
import se.ltu.kaicalib.core.domain.entities.Loan;
import se.ltu.kaicalib.core.domain.entities.Patron;
import se.ltu.kaicalib.core.domain.entities.Receipt;
import se.ltu.kaicalib.core.service.CheckoutService;
import se.ltu.kaicalib.core.service.CopyService;
import se.ltu.kaicalib.core.service.LoanService;
import se.ltu.kaicalib.core.service.PatronService;

import static se.ltu.kaicalib.core.utils.GenUtil.toArr;


/**
 * Routes controller specific for actions concerning loans.
 *
 * These are restricted to the Role PATRON.
 *
 * todo add Session-based message feedback to avoid URL formatting corruption of titles
 * todo the Controller should perhaps be called CheckoutController and some routes be moved out of here.
 *
 *
 * @author
 */
@Controller
@Secured("PATRON")
public class LoanController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private PatronService patronService;
    private LoanService loanService;
    private CopyService copyService;
    private CheckoutList checkoutList;
    private CheckoutService checkoutService;
    private MessageSource messageSource;


    @Autowired
    public LoanController(
        PatronService patronService,
        LoanService loanService,
        CopyService copyService,
        CheckoutList checkoutList,
        CheckoutService checkoutService,
        MessageSource messageSource) {
        this.patronService = patronService;
        this.loanService = loanService;
        this.copyService = copyService;
        this.checkoutList = checkoutList;
        this.checkoutService = checkoutService;
        this.messageSource = messageSource;
    }


    /* ====== COPY and TITLES ================================= */

    @GetMapping("/checkout_add_title")
    public String addCopyToCheckout(@RequestParam("id") Long titleId, Model model) {
        Copy copy = loanService.getCopyForNewLoan(titleId); // todo receive selected copy to add to checkout not a title.
        checkoutList.addCopyId(copy.getId());               // todo business rules checks and feedback on failure
        String msg = messageSource.getMessage("Copy.addedToCheckout.success", toArr(titleId.toString()), null);
        model.addAttribute("success", msg);

        return "redirect:search";
    }


    @GetMapping("/checkout_remove_copy")
    public String removeCopyFromCheckout(@RequestParam("id") Long copyId, Model model) {
        checkoutList.removeCopyId(copyId);
        String msg = messageSource.getMessage("Copy.removedFromCheckout.success", toArr(copyId.toString()), null);
        model.addAttribute("success", msg);

        return "redirect:checkout";
    }



    /* ====== LOANS list and renew ============================ */

    @GetMapping("/checkout_renew_loan")
    public String renewLoan(@RequestParam("id") Long id, Model model) {
        checkoutList.addLoanId(id);                         // todo business rules checks and feedback on failure
        String msg = messageSource.getMessage("Loan.addedToCheckout.success", null, null);
        model.addAttribute("success", msg);

        return "redirect:view_loans";
    }


    @GetMapping("/checkout_remove_loan")
    public String removeLoanRenewalFromCheckout(@RequestParam("id") Long loanId, Model model) {
        checkoutList.removeLoanId(loanId);
        String msg = messageSource.getMessage("Loan.removedFromCheckout.success", toArr(loanId.toString()), null);
        model.addAttribute("success", msg);

        return "redirect:checkout";
    }


    @GetMapping("/patron/view_loans")
    public String viewLoans(Model model) {
        model.addAttribute("loans", loanService.getAllLoansForPatron());

        return "/core/view_loans";
    }



    /* ====== CHECKOUT ======================================== */
    // todo Devise safe way of passing the object instances generated for the GetMapping to
    //  the PostMapping. What happens to flashAttributes if the user leaves view_checkout and
    //  never activates the PostMapping until later. Need to satisfy memory efficiency concerns.

    @GetMapping("/checkout")
    public String viewCheckout(Model model) {
        model.addAttribute("copies", copyService.getAllCopiesByIdList(checkoutList.getCopiesIds()));
        model.addAttribute("loans",  loanService.getAllLoansByIdList(checkoutList.getLoansIds()));

        return "/core/view_checkout";
    }


    @PostMapping("/checkout")
    public String proceedWithCheckout(
        @RequestParam(value = "usageTerms") Boolean usageTerms,
        Model model,
        RedirectAttributes redirectAttrs) {

        if (usageTerms) {
            // todo This is cutting corners.
            //  There should be a redirect on success and receipt and msg passed in Session.
            //  Another good usage of flashAttributes.
            //
            // Testing now how this all works with a different return quite simply.
            Receipt receipt = checkoutService.checkOut(checkoutList, patronService.getPatronForAuthUser());
            String msg = messageSource.getMessage("CheckoutList.checkout.success", toArr(String.valueOf(receipt.gettotalLoanCount())), null);
            model.addAttribute("success", msg);
            model.addAttribute("loans", receipt.getLoanlist());
            model.addAttribute("renewedLoans", receipt.getRenewedLoanList());
            //model.addAttribute("receipt", receipt);
            //redirectAttrs.addFlashAttribute("receipt", receipt);

            return "/core/view_receipt";//return "return:receipt";
        }
        String msg = messageSource.getMessage("CheckoutList.checkout.termsAndConditions", null, null);
        model.addAttribute("warn", msg);

        return "core/view_checkout";
    }


    @GetMapping("/receipt")
    public String viewReceipt(@ModelAttribute("flashAttribute") Receipt receipt, Model model) {
        model.addAttribute("loans", receipt.getLoanlist());
        model.addAttribute("renewedLoans", receipt.getRenewedLoanList());
        // todo set up the template.

        // todo Compile with H2 database for easy deploy on VPS.
        return "/core/view_receipt";

    }
}
