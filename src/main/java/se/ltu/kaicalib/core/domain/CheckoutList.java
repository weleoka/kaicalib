package se.ltu.kaicalib.core.domain;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * This holds a number of selected Titles for the patron
 * much like a shopping cart.
 *
 * The implementation is stored in session, although that is very
 * memory-heavy for the server, there are a number of different methods
 * for achieving a similar result, all have their pros and cons.
 * For an introduction/overview look at: https://stackoverflow.com/a/18795626/3092830
 *
 * Consideration should be made on weather it is sensible to store a list of Copy objects
 * or just their ids. Does size matter? How heavy is it going to be to process a checkout
 * all in one big pile? How is the user going to appreciate being told that what they though was
 * available is not longer because they made it to the checkout too slowly, i.e. does temporary
 * reserving become important?
 *
 * Interestingly @PostConstruct or @PreDestroy can be applied to this "HTTP Session management by bean" technique.
 *
 * @author
 *
 */
@Component
@SessionScope // equivalent to @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class CheckoutList implements Serializable {
    private List<Long> copiesIds = new ArrayList<>();
    private List<Long> loansIds = new ArrayList<>(); // These are loans which are to be renewed



    /* ====== COPY ======================================== */
    public void addCopyId(Long copyId) {
        this.copiesIds.add(copyId);
    }


    public void removeCopyId(Long copyId) {
        this.copiesIds.remove(copyId);
    }


    public void emptyCheckoutCopyIds() {
        this.copiesIds.clear();
    }



    /* ====== LOAN ======================================== */
    public void addLoanId(Long loanId) {
        this.loansIds.add(loanId);
    }


    public void removeLoanId(Long loanId) {
        this.loansIds.remove(loanId);
    }


    public void emptyCheckoutLoanIds() {
        this.loansIds.clear();
    }
}



/*
    // todo Possible ways of storing objects as well for passing,
    //  like a flashAttribute for example. The only reason would be to save on db reads.

    public List<Title>getAllTitlesForCopiesIds() {
        List<Title> tmpList = new ArrayList<>();

        return tmpList;
    }

    public List<Title> addTitleToCheckout(Title title) {
        this.titlesList.add(title);

        return titlesList;
    }


    public List<Long> addTitleIdToCheckout(Long titleId) {
        this.titlesIds.add(titleId);

        return titlesIds;
    }


    public void emptyCheckoutTitles() {
        this.titlesList.clear();
    }
 */
