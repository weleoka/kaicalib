package se.ltu.kaicalib.core.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("REF")
public class RefCopyType extends CopyType {

    /**
     * Required Hibernate no-args-constructor.
     */
    public RefCopyType() {}

    public RefCopyType(Copy copy) {
        super.setCopy(copy);
        super.setLoanTimeInWeeks(0);
    }
}
