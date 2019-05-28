package se.ltu.kaicalib.core.domain.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * todo right now each Copy has its own CopyType object, which is not good. Should use flyweight pattern here.
 */
@Entity
@Table(name = "Copy_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_discriminator")
public abstract class CopyType {

    @Id
    @GeneratedValue
    @Column(name = "copy_type_id", updatable = false, nullable = false)
    private Long id;

    @Type(type="uuid-binary")
    @Column(nullable=false, columnDefinition="BINARY(16)")
    final private UUID uuid = UUID.randomUUID();

    @OneToOne(cascade = CascadeType.ALL)
    private Copy copy;

    @Basic
    @Column(name = "loan_time")
    int loanTimeInWeeks;

    /**
     * Required Hibernate no-args-constructor.
     */
    protected CopyType() {
    }

    /**
     * Required Hibernate no-args-constructor.
     */
    protected CopyType(Copy copy) {
        this.copy = copy;
    }





    // ********************** Accessor Methods ********************** //

    protected void setCopy(Copy copy){
        this.copy = copy;
    }

    protected void setLoanTimeInWeeks(int loanTimeInWeeks) {
        this.loanTimeInWeeks = loanTimeInWeeks;
    }

    public int getLoanTimeInWeeks() {
        return this.loanTimeInWeeks;
    }

    // ********************** Business Methods ********************** //
    public Copy loan() {

        //TODO PLACEHOLDER
        return null;
    }

    public Copy reserve() {

        //TODO PLACEHOLDER
        return null;
    }

    // ********************** Common Methods ********************** //

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof CopyType)) {
            return false;
        }
        CopyType copyType = (CopyType) obj;
        return uuid != null && uuid.equals(copyType.uuid);
    }

    public int hashCode() {
        return Objects.hash(uuid);
    }

}
