package se.ltu.kaicalib.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.ltu.kaicalib.core.domain.Title;

import java.util.List;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {

    /**
     * Searches for the substring in the title field and returns the first 20 hits.
     *
     * @param name      the substring entered in the searchfield
     * @return          a list of the 20 first hits
     */
    public List<Title> findFirst20ByNameContaining(String name);

    public List<Title> findByNameContaining(String name);

    /**
     * todo READ MORE! DECIDE
     * exactly how the repositories/services need to cooperate to achieve this is uncertain
     * my current idea is to have an author repository implement a similar search method
     * and a SearchService-class which can in some way coordinate the results.
     *
     * it would be really interesting to read more about how to do smart searching on multiple
     * attributes.
     */

    /**
     *


    public Title findTitleByAuthor(Author author);

    // ********************** JpaRepository methods ********************** //

    // public Title findById(Long id);
    // public void save(Title title)
     */
}
