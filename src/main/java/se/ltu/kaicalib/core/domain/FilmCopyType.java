package se.ltu.kaicalib.core.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FILM")
public class FilmCopyType extends CopyType {

    /**
     * Required Hibernate no-args-constructor.
     */
    public FilmCopyType() {}

    public FilmCopyType(Copy copy) {
        super.setCopy(copy);
        //TODO fetch value from DB instead
        super.setLoanTimeInWeeks(1);
    }
}
