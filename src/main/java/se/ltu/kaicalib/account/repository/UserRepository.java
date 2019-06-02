package se.ltu.kaicalib.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.ltu.kaicalib.account.domain.User;
import se.ltu.kaicalib.core.domain.CopyDTO;
import se.ltu.kaicalib.core.domain.entities.Title;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);
}
