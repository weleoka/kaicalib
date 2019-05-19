package se.ltu.kaicalib.account.service;

import se.ltu.kaicalib.account.domain.User;
import se.ltu.kaicalib.account.utils.RoleNotValidException;

import java.util.List;

public interface UserService {

    User findUserByEmail(String email);
    User findUserByUsername(String username);
    List<User> findAllUsers();
    void saveUser(User user, String roleStr) throws RoleNotValidException;
    void updateUser(User user);
}
