package se.ltu.kaicalib.core.domain.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Loan class containing title specific information.
 */

@Entity
@Table(name = "Loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Type(type="uuid-binary")
    @Column(nullable=false, columnDefinition="BINARY(16)")
    final private UUID uuid = UUID.randomUUID();

    @Column(name = "loan_return_date")
    private LocalDate returnDate;

    //todo this might not work as intended if the entity is not persisted before it's used? Think!
    @Column(name = "created_at")
    private LocalDate createdAt;

    //TODO currently using 1-1 on loan, so each loan is of a single Copy
    @OneToOne(fetch = FetchType.EAGER, optional = false) // , cascade = CascadeType.ALL) // only status to set non-availalbe, better handle internally and not cascade.
    private Copy copy;

    //@OneToOne(fetch = FetchType.EAGER, optional = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patron_id")
    //@Column(name = "patron_id")
    private Patron patron;


    /**
     * Required Hibernate no-args-constructor.
     *
     * Note that this instantly creates the loan.
     *
     */
    Loan() {}


    /**
     * Creates a new loan
     *
     * Note also that it calls makeLoan and creates the loan directly.
     * To make a loan and not initialise it with dates call empty constructor and
     * use setters to update properties, to then call makeLoan() when ready.
     *
     * Depreciated as is creates the inefficient @ManyToOne table, instead create the loan with a copy
     * object and then: patron.addLoan(loan); to the Patron.
     * @param copy
     * @param patron
     */
    public Loan(Copy copy, Patron patron) {
        this.copy = copy;
        this.patron = patron;
        makeLoan();
    }


    /**
     * Constructor for creating a loan.
     *
     * This new loan should then be added to a Patron.
     *
     * @param copy
     */
    public Loan(Copy copy) {
        this.copy = copy;
        makeLoan();
    }



    // ********************** Accessor Methods ********************** //
    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }

    public Copy getCopy() { return copy; }

    public void setCopy(Copy copy) { this.copy = copy; }

    public LocalDate getCreatedAt() { return createdAt; }

    public LocalDate getReturnDate() { return returnDate; }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    private void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }



    // ********************** Model Methods ********************** //

    //TODO research how @PrePersist reacts on updates.
    //TODO possibly don't make the loan on persist and change state beforehand so the Loan is ready.
    @PrePersist
    void createdAt() {
        this.createdAt = LocalDate.now();
        this.makeLoan();
    }

    //Loan->Copy->CopyType-(int)->Copy-(int)->Loan.makeLoan(int)
    private void makeLoan() {
        int loanTime = copy.getLoanTimeInWeeks();
        LocalDate returnDate = LocalDate.now().plusWeeks(loanTime);
        this.setReturnDate(returnDate);
    }


    /**
     * Renews the loan as of from the expiration of the old
     * loan period depending on the loan period specified in copy.
     *
     * This could perhaps call some business rules checks internally, if for example
     * the loanRenewalCount == 3 then don't renew. The trouble is passing the failure
     * codes out again.
     */
    public void renewLoan() {
        int loanTime = copy.getLoanTimeInWeeks();
        LocalDate originalReturnDate = getReturnDate();
        LocalDate newReturnDate = originalReturnDate.plusWeeks(loanTime);
        this.setReturnDate(newReturnDate);
    }


    // ********************** Common Methods ********************** //
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return uuid.equals(loan.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}


/*

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Loan)) {
            return false;
        }
        Loan loan = (Loan) obj;
        return uuid != null && uuid.equals(loan.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }






 Decided against using this as it's better to reuse the old loan object than
 to create a new one. More attributes can be added to old loan object to keep track
 of it renewals etc.
 */
    /**
     * Creates a new loan from an old one.
     * The new one is made with a start date from the
     * return date of the old one.
     * Note also that it internally calls
     * renewLoan() to set temporal properties.
     *
     * @param loan the Loan object that is used to create the new one
     */
    /*
    public Loan(Loan loan) {
        this.copy = loan.getCopy();
        this.patron = getPatron();
        renewLoan();
    }
    /*
 */
