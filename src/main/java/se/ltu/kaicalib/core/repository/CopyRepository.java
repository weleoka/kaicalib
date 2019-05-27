package se.ltu.kaicalib.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.ltu.kaicalib.core.domain.CopyDTO;
import se.ltu.kaicalib.core.domain.entities.Copy;
import se.ltu.kaicalib.core.domain.entities.Title;

import java.util.List;


@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {

    @Query("SELECT c.id, c.uuid, c.status, c.copyType FROM Copy c WHERE c.title = :title")
    List<CopyDTO> findAllCopiesByTitleDTO(@Param("title")Title title);

    @Query("SELECT c FROM Copy c WHERE c.title = :title")
    List<Copy> findAllCopiesByTitle(@Param("title")Title title);

    @Query("SELECT c FROM Copy c WHERE c.title = :title AND status = 'available'")
    List<Copy> findAllAvailableCopiesByTitle(@Param("title")Title title);

    @Query("SELECT c FROM Copy c WHERE c.title.id = :titleId AND status = 'available'")
    List<Copy> findAllAvailableCopiesByTitleId(@Param("titleId")Long titleId);
}
