package se.ltu.kaicalib.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.ltu.kaicalib.core.domain.entities.Patron;

import java.util.UUID;


/**
 * Patron entity
 *
 */
@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
    //Patron getPatronByUserUuid(UUID userUuid);

    @Query("SELECT p FROM Patron p WHERE p.userUuid = :userUuid")
    Patron getPatronByUserUuid(@Param("userUuid")UUID userUuid);
}
