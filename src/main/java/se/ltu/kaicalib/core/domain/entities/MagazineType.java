package se.ltu.kaicalib.core.domain.entities;

import javax.persistence.*;

/**
 * This way of handling the TitleType and subclasses is a bit overkill with current requirements, but building it this
 * way just to try it out. Having a TitleType with inheritance and a single unique one-one relationship per TitleType
 * subclass is kind of dumb.
 */
@Entity
@DiscriminatorValue("MAGA")
public class MagazineType extends TitleType{

    @Basic
    @Column(name = "issn", nullable = false, unique = true)
    private String issn;

    @OneToOne
    private Publisher publisher;

    public MagazineType() {
    }

    /**
     * Constructor.
     * @param publisher the publisher of the book
     * TODO think about the order of object creation here and how this is to be implemented in the thymeleaf forms.
     * TODO issn validation and making issn/naturalID classes.
     */
    public MagazineType(Publisher publisher, String issn) {
        this.issn = issn;
        this.publisher = publisher;
    }

    // ********************** Accessor Methods ********************** //

    // ********************** Business Methods ********************** //

    // ********************** Common Methods ********************** //

    /**
     * Inherited from TitleType
     */
}
