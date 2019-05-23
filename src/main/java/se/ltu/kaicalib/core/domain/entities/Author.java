package se.ltu.kaicalib.core.domain.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Author")
public class Author {

    @Id
    @GeneratedValue
    @Column(name = "author_id", updatable = false, nullable = false)
    private Long id;

    @Type(type="uuid-char")
    @Column(nullable=false, unique=true)
    final private UUID uuid = UUID.randomUUID();

    @Basic
    @Column(name = "first_name")
    private String firstName;

    @Basic
    @Column(name = "last_name")
    private String lastName;

    //TODO This could also be a class, or an enum, but I don't think we want to go any further
    //     down that rabbit hole
    @Basic
    @Column(name = "nationality")
    private String nationality;

    private LocalDate createdAt;

    // ********************** Accessor Methods ********************** //

    // ********************** Model Methods ********************** //

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDate.now();
    }


    // ********************** Common Methods ********************** //



    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Author)) {
            return false;
        }
        Author author = (Author) obj;
        return uuid != null && uuid.equals(author.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
