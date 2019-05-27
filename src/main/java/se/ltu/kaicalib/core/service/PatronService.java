package se.ltu.kaicalib.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ltu.kaicalib.account.domain.User;
import se.ltu.kaicalib.account.service.UserServiceImpl;
import se.ltu.kaicalib.core.domain.entities.Patron;
import se.ltu.kaicalib.core.repository.PatronRepository;


/**
 * Facilitates interacting with Patron on the persistence level
 * in regards to authentication.
 *
 *
 * @author
 */
@Transactional(value = "coreTransactionManager")
@Service
public class PatronService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private UserServiceImpl userService;
    private PatronRepository patronRepository;


    @Autowired
    public PatronService(
        UserServiceImpl userService,
        PatronRepository patronRepository) {
        this.userService = userService;
        this.patronRepository = patronRepository;
    }


    @Transactional(value = "coreTransactionManager", readOnly = true)
    public Patron getPatronForAuthUser() {
        User user = userService.getAuthUser();
        Patron patron = patronRepository.getPatronByUserUuid(user.getUuid());

        return patron;
    }
}