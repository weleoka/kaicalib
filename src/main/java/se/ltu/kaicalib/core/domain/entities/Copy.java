package se.ltu.kaicalib.core.domain.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;


/**
 * Represents a copy of a title in the library.
 *
 *
 * todo status should be ENUM type.
 */
@Entity
@Table(name = "Copy")
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "copy_id", updatable = false, nullable = false)
    private Long id;

    @Type(type="uuid-binary")
    @Column(nullable=false, columnDefinition="BINARY(16)")
    final private UUID uuid = UUID.randomUUID();

    @Basic
    @Column(name = "curr_status")
    private String status;

    @OneToOne(fetch = FetchType.EAGER) //, cascade = CascadeType.ALL) // commented out as nothing happening to Copy should affect Title
    @Fetch(FetchMode.JOIN)
    private Title title;

    @OneToOne(fetch = FetchType.EAGER) //(cascade = CascadeType.ALL) // commented out because no matter what you do with Copy CopyType never has to change!
    private CopyType copyType;

    //TODO store on loan only? K: I think yes, anything to reduce the need to cascade... only status still needs to be updated on copy.
    //@Column(name = "copy_return_date")
    //private LocalDate returnDate;

    @Basic
    @Column(name = "copy_created_at")
    private LocalDate createdAt;

    /**
     * Required Hibernate no-args-constructor.
     */
    public Copy() {}


    /**
     * Constructor.
     * TODO lots of things to consider here, will require extensive refactoring as the logic develops
     * TODO might not even be used
     */
    public Copy(Title title, String type) {
        this.title = title;
        this.status = "available";
        //todo placeholder, add logic
        //TODO ugly placeholder logic, refactor
        if (type.equals("reference")) {
            this.copyType = new RefCopyType();
        } else if (type.equals("course")) {
            this.copyType = new CourseCopyType();
        } else if (type.equals("normal")) {
            this.copyType = new NormalCopyType();
        } else if (type.equals("film")) {
            this.copyType = new FilmCopyType();
        } else {
            throw new InvalidParameterException();
        }
    }



    // ********************** Accessor Methods ********************** //
    public Long getId() {
        return this.id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTitle(Title title) { this.title = title; }

    public Title getTitle() { return title; }

    public void setCopyType(CopyType copyType) { this.copyType = copyType; }

    public CopyType getCopyType() { return this.copyType; }



    // ********************** Model Methods ********************** //

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDate.now();
    }

    //TODO ask CopyType object for loan time data for that object.
    public int getLoanTimeInWeeks() {return this.copyType.getLoanTimeInWeeks(); }



    // ********************** Common Methods ********************** //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Copy copy = (Copy) o;
        return uuid.equals(copy.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}


/*
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Copy)) {
            return false;
        }
        Copy copy = (Copy) obj;
        return uuid != null && uuid.equals(copy.uuid);
    }



    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
 */
