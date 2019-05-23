package se.ltu.kaicalib.core.domain;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import se.ltu.kaicalib.core.domain.entities.Title;

import java.io.Serializable;
import java.util.List;


/**
 * This holds a number of selected Titles for the patron
 * much like a shopping cart.
 *
 * The implementation is stored in session, although that is very
 * memory-heavy for the server, there are a number of different methods
 * for achieving this. For an introduction/overview look at:
 * https://stackoverflow.com/a/18795626/3092830
 *
 * Consideration should be made on weather it is sensible to store a list of Copy objects
 * or just their ids. Does size matter? How heavy is it going to be to process a checkout
 * all in one big pile? How is the user going to appreciate being told that what they though was
 * available is not longer because they made it to the checkout too slowly, i.e. does temporary
 * reserving become important?
 *
 * todo A lot of provisional/experimental clutter to remove
 *
 */
@Component
@Scope("checkout-list")
@Data
public class CheckoutList implements Serializable {

    private List<Title> titlesList;
    private List<Long> titlesIds;
    private List<Long> copiesIds;
    private List<Long> loanIds; // These are loans which are to be renewed


    public List<Title> addTitleToCheckout(Title title) {
        this.titlesList.add(title);

        return titlesList;
    }


    public List<Long> addTitleIdToCheckout(Long titleId) {
        this.titlesIds.add(titleId);

        return titlesIds;
    }


    public List<Long> addCopyIdToCheckout(Long copyId) {
        this.copiesIds.add(copyId);

        return copiesIds;
    }


    public List<Long> addLoanIdToCheckout(Long loanId) {
        this.copiesIds.add(loanId);

        return loanIds;
    }


    public void emptyCheckoutTitles() {
        this.titlesList.clear();
    }


    public void emptyCheckoutTitleIds() {
        this.titlesIds.clear();
    }


    public void emptyCheckoutCopyIds() {
        this.copiesIds.clear();
    }
}
