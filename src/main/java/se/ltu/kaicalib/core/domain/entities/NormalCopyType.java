package se.ltu.kaicalib.core.domain.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("NORMAL")
public class NormalCopyType extends CopyType {

    /**
     * Required Hibernate no-args-constructor.
     */
    public NormalCopyType() {}

    public NormalCopyType(Copy copy) {
        super.setCopy(copy);
        //TODO fetch value from DB instead
        //TODO specs actually specify one month, not 4 weeks
        super.setLoanTimeInWeeks(4);
    }
}
