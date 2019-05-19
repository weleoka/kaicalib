package se.ltu.kaicalib.core.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("COURSE")
public class CourseCopyType extends CopyType {

    /**
     * Required Hibernate no-args-constructor.
     */
    public CourseCopyType() {}

    public CourseCopyType(Copy copy) {
        super.setCopy(copy);
        //TODO fetch value from DB instead
        super.setLoanTimeInWeeks(2);
    }
}
