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
@DiscriminatorValue("FILM")
public class FilmType extends TitleType{

    @Basic
    @Column(name = "isan", nullable = false, unique = true)
    private String isan;

    //Just a fraction of all information that could be stored about a movie
    @ElementCollection
    @CollectionTable(name = "Actors", joinColumns = @JoinColumn(name = "actor_id"))
    @Column(name = "actor_name")
    private List<String> actors;

    /**
     * Constructor.
     *
     * @param actors a list of all actors who are credited in the film
     * TODO think about the order of object creation here and how this is to be implemented in the thymeleaf forms.
     */
    public FilmType(String isan, LinkedList<String> actors) {
        this.isan = isan;
        this.actors = actors;
    }


    // ********************** Accessor Methods ********************** //

    // ********************** Business Methods ********************** //

    // ********************** Common Methods ********************** //

    /**
     * Inherited from TitleType
     */
}
