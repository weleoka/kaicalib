package se.ltu.kaicalib.core.domain.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


/**
 * Representing a patron that can interact with the library system.
 *
 * Debts, loans, reservations, receipts all go back to this entity.
 *
 * todo Make UUID into BINARY(16), for for all UUID's in fact.
 */
@Entity
@Table(name = "Patron")
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "patron_id", updatable = false, nullable = false)
    private Long id;

    @Type(type="uuid-binary")
    @Column(nullable=false, columnDefinition="BINARY(16)")
    final private UUID uuid = UUID.randomUUID();

    @OneToMany(
        mappedBy = "patron",        // i.e. patron attribute in Loan
        fetch = FetchType.EAGER
        //cascade = CascadeType.ALL // todo If a patron is deleted all their loans are as well... good or bad.
    )
    //@JoinColumn(name = "patron_id")
    //@ManyToOne(fetch = FetchType.LAZY)
    //@OrderBy("returnDate DESC")
    private List<Loan> loans;

    private LocalDate createdAt; // Need this really?

    @Type(type="uuid-binary")
    @Column(name = "user_uuid", nullable = false, columnDefinition="BINARY(16)")
    private UUID userUuid;

    /**
     * Required Hibernate no-args-constructor.
     */
    public Patron() {}

    public Patron(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public Patron(ArrayList<Loan> loans) {
        this.loans = loans;
    }



    // ********************** Accessor Methods ********************** //

    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }



    // ********************** Model Methods ********************** //

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDate.now();
    }

    void makeLoan(Copy copy) throws InvalidObjectException {
        if (copy.getLoanTimeInWeeks() == 0) {
            throw new InvalidObjectException("Magazines and reference literature can not be loaned");
        }
        this.loans.add(new Loan(copy,this));
    }

    //public void addLoan(Loan loan) { this.loans.add(loan); }

    public void addLoan(Loan loan) {
        loans.add(loan);
        loan.setPatron(this);
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
        loan.setPatron(null);
    }



    // ********************** Common Methods ********************** //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patron patron = (Patron) o;
        return uuid.equals(patron.uuid);
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
        if(!(obj instanceof Patron)) {
            return false;
        }
        Patron patron = (Patron) obj;
        return uuid.equals(patron.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
 */
