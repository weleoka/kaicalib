package se.ltu.kaicalib.core.domain.entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * This way of handling the TitleType and subclasses is a bit overkill with current requirements, but building it this
 * way just to try it out. Having a TitleType with inheritance and a single unique one-one relationship per TitleType
 * subclass is kind of dumb.
 */
@Entity
@DiscriminatorValue("BOOK")
public class BookType extends TitleType{

    @Basic
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @OneToOne
    private Publisher publisher;

    @OneToMany
    private List<Author> authors;

    /**
     * Constructor for multiple authors.
     *
     * @param publisher publisher of the book
     * @param authors the authors of the book, require LinkedList to stop the lists from being of different types.
     * TODO think about the order of object creation here and how this is to be implemented in the thymeleaf forms.
     */
    public BookType(String isbn, Publisher publisher, LinkedList<Author> authors) {
        this.isbn = isbn;
        //TODO add logic for the same title having different publishers some time in a remote future.
        this.publisher = publisher;
        this.authors = authors;
    }

    /**
     * Constructor for one author.
     * @param publisher publisher of the book
     * @param author the author of the book
     * TODO think about the order of object creation here and how this is to be implemented in the thymeleaf forms.
     */
    public BookType(String isbn, Publisher publisher, Author author) {
        this.isbn = isbn;
        //TODO add logic for the same title having different publishers some time in a remote future.
        this.publisher = publisher;
        //Setting authors to null to avoid surprises
        //TODO think through if it's not better to expose any bugs that pop up here instead of hiding them.
        authors = null;
        authors = new LinkedList<Author>();
        authors.add(author);
    }

    // ********************** Accessor Methods ********************** //

    // ********************** Business Methods ********************** //

    // ********************** Common Methods ********************** //

    /**
     * Inherited from TitleType
     */
}
