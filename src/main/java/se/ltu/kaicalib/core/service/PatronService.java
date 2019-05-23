package se.ltu.kaicalib.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ltu.kaicalib.account.domain.User;
import se.ltu.kaicalib.account.service.UserServiceImpl;
import se.ltu.kaicalib.core.domain.entities.Patron;
import se.ltu.kaicalib.core.repository.PatronRepository;

@Transactional(value = "coreTransactionManager", readOnly = true)
@Service
public class PatronService {

    private UserServiceImpl userService;

    PatronRepository patronRepository;

    @Autowired
    PatronService(UserServiceImpl userService, PatronRepository patronRepository) {
        this.userService = userService;
        this.patronRepository = patronRepository;
    }

    public Patron getPatronForAuthUser() {
        User user = userService.getAuthUser();

        return patronRepository.getPatronByUserUuid(user.getUuid());
    }
}
