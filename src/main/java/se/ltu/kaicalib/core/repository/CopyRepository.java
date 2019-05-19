package se.ltu.kaicalib.core.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.ltu.kaicalib.core.domain.Copy;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {}
