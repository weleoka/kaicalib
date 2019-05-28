package se.ltu.kaicalib.core.domain.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Title class containing title specific information.
 */

@Entity
@Table(name = "Title")
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "title_id", updatable = false, nullable = false)
    private Long id;

    @Type(type="uuid-binary")
    @Column(nullable=false, columnDefinition="BINARY(16)")
    final private UUID uuid = UUID.randomUUID();

    @Basic
    @Column(name = "title_name")
    private String name;

    //todo this might not work as intended if the entity is not persisted before it's used? Think!
    @Column(name = "created_at")
    private LocalDate createdAt;

    /**
     * Required Hibernate no-args-constructor.
     */
    public Title() {}


    /**
     * Constructor.
     * TODO lots of things to consider here, will require extensive refactoring as the logic develops
     * TODO might not even be used
     */
    public Title(String name) {
        this.name = name;
    }

    // ********************** Accessor Methods ********************** //

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }


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
        if(!(obj instanceof Title)) {
            return false;
        }
        Title title = (Title) obj;
        return uuid != null && uuid.equals(title.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
