package se.ltu.kaicalib.core.domain.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Title_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_discriminator")
public abstract class TitleType {

    @Id
    @GeneratedValue
    @Column(name = "title_type_id", updatable = false, nullable = false)
    private Long id;

    @Type(type="uuid-binary")
    @Column(nullable=false, columnDefinition="BINARY(16)")
    final private UUID uuid = UUID.randomUUID();

    /**
     * Required Hibernate no-args-constructor.
     */
    protected TitleType() {}

    // ********************** Accessor Methods ********************** //

    // ********************** Business Methods ********************** //

    // ********************** Common Methods ********************** //

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof TitleType)) {
            return false;
        }
        TitleType titleType = (TitleType) obj;
        return uuid != null && uuid.equals(titleType.uuid);
    }

    public int hashCode() {
        return Objects.hash(uuid);
    }

}
