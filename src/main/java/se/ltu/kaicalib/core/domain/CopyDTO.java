package se.ltu.kaicalib.core.domain;

import lombok.Data;
import se.ltu.kaicalib.core.domain.entities.CopyType;

import java.util.Objects;
import java.util.UUID;

/**
 * This class would be used if only passing certain parts of the object
 * onwards, hence not requiring a full reconstruction of an object from persistence.
 *
 * It would be combined with data about other relevant objects. Which title it "owns"
 * and the likes.
 *
 * Decided is that this is completely unnecessary for this project as everything
 * has too be passed down into an HTTP layer anyhow. If moving json then possibly DTO's come
 * into play for providing an API endpoint with all it's data in a convenient form.
 */
@Data
public class CopyDTO {

    private Long id;
    private UUID uuid;
    private String status;
    //private Title title; // ould be a good idea at some point
    private CopyType copyType;

    /**
     * Required Hibernate no-args-constructor.
     */
    public CopyDTO() {}

    public CopyDTO(Long id, String uuid, String status, CopyType copyType) {
        this.id = id;
        this.uuid = UUID.fromString(uuid);
        this.status = status;
        this.copyType = copyType;
    }


// ********************** Common Methods ********************** //

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof CopyDTO)) {
            return false;
        }
        CopyDTO copy = (CopyDTO) obj;
        return uuid != null && uuid.equals(copy.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
