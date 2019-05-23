package se.ltu.kaicalib.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.ltu.kaicalib.core.domain.entities.CopyType;

@Repository
public interface CopyTypeRepository extends JpaRepository<CopyType, Long> {}
