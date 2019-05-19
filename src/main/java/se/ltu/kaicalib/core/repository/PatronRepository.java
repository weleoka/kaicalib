package se.ltu.kaicalib.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.ltu.kaicalib.core.domain.Patron;

/**
 * Unsure about how to retrieve the correct user object from the db here since I don't know how
 * login will be handled.
 *
 * Maybe using the standard JpaRepository method findOne(Long id) to get the user is enough.
 *
 * TODO add correct fetch method, also note that currently all Loan:s in User.loans are eager-fetched.
 * TODO we might want to retrieve only active loans instead.
 */
@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {

}
