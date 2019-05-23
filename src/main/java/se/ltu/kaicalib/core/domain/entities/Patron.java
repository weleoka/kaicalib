package se.ltu.kaicalib.core.domain.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Patron")
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "patron_id", updatable = false, nullable = false)
    private Long id;

    @Type(type="uuid-char")
    @Column(nullable=false, unique=true)
    final private UUID uuid = UUID.randomUUID();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("returnDate DESC")
    private List<Loan> loans;

    private LocalDate createdAt; // Need this really?

    @Column(name = "user_uuid")
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

    public void addLoan(Loan loan) { this.loans.add(loan); }

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


    // ********************** Common Methods ********************** //

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Patron)) {
            return false;
        }
        Patron patron = (Patron) obj;
        return uuid != null && uuid.equals(patron.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
