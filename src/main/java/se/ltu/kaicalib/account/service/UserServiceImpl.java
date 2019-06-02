package se.ltu.kaicalib.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ltu.kaicalib.account.domain.Role;
import se.ltu.kaicalib.account.domain.User;
import se.ltu.kaicalib.account.repository.RoleRepository;
import se.ltu.kaicalib.account.repository.UserRepository;
import se.ltu.kaicalib.account.utils.RoleNotValidException;
import se.ltu.kaicalib.core.domain.entities.Patron;

import java.util.*;


@Service
@Transactional(value = "accountsTransactionManager")
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
        UserRepository userRepository,
        RoleRepository roleRepository,
        PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(value = "accountsTransactionManager", readOnly = false)
    public void saveUser(User user, String roleStr) throws RoleNotValidException {
        Role role = roleRepository.findByRole(roleStr);
        user.setRoles(new HashSet<Role>(Arrays.asList(role)));

        if (role == null) {

            throw new RoleNotValidException("User being assigned invalid role of type: " + roleStr);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Patron patron = new Patron(user.getUuid());
        user.setPatronUuid(patron.getUuid());
/*        Set<Role> roles = new HashSet<>(); // multiple roles not supported yet
        roles.add(role);
        user.setRoles(roles);*/
        userRepository.save(user);
        logger.debug("Created new user (ID: {}, with roles: {})", user.getUsername(), user.getRoles());
    }

    @Override
    @Transactional(value = "accountsTransactionManager", readOnly = false)
    public void updateUser(User user) {
        userRepository.saveAndFlush(user);
    }



    /* ============== LOGIN PROCESS ================= */
    // todo Make it possible to have multiple different roles...for fun? Currently only allow the first role in set (no order).
    /**
     * Standard route of logging in a user object from a login form.
     *
     * @param user
     */
    public void login(User user) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(user));
    }


    private Authentication authenticate(User user) {

        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), Collections.singleton(createAuthority(user)));
    }


    private GrantedAuthority createAuthority(User user) {
        Set<Role> roles = user.getRoles();
        String roleStr = "DEFAULT_BUT_INVALID_ROLE";

        for (Role role : roles) {
            roleStr = role.toString();

            break; // only get the first role from the set.
        }

        return new SimpleGrantedAuthority(roleStr);
    }


    /**
     * Convenience method to return the current auth user
     * from the security context.
     *
     * @return
     */
    public User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return findUserByUsername(username);
    }
}
