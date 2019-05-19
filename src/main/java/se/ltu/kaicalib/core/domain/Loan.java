package se.ltu.kaicalib.core.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Title class containing title specific information.
 */

@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Type(type="uuid-char")
    @Column(nullable=false, unique=true)
    final private UUID uuid = UUID.randomUUID();

    private LocalDate returnDate;

    //todo this might not work as intended if the entity is not persisted before it's used? Think!
    private LocalDate createdAt;

    //TODO currently using 1-1 on loan, so each loan is of a single Copy
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    private Copy copy;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    private Patron patron;

    /**
     * Required Hibernate no-args-constructor.
     */
    public Loan() {}

    /**
     * Required Hibernate no-args-constructor.
     */
    public Loan(Copy copy, Patron patron) {
        this.copy = copy;
        this.patron = patron;
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

        LocalDate newReturnDate = LocalDate.now().plusWeeks(loanTime);

        this.setReturnDate(newReturnDate);

    }

    // ********************** Common Methods ********************** //

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
}
