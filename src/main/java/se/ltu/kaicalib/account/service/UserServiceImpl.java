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
import se.ltu.kaicalib.core.domain.Patron;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


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

        logger.debug("Created new user (ID: {}, with roles: {})", user.getUsername(), user.getRoles());

/*        Set<Role> roles = new HashSet<>(); // multiple roles not supported yet
        roles.add(role);
        user.setRoles(roles);*/

        userRepository.save(user);
    }

    @Override
    @Transactional(value = "accountsTransactionManager", readOnly = false)
    public void updateUser(User user) {
        userRepository.saveAndFlush(user);
    }


    // todo not sure if the following auth methods belong in this service bean.
    public void login(User user) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(user));
    }

    private Authentication authenticate(User user) { //user.getPassword()
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), Collections.singleton(createAuthority(user)));
    }

    private GrantedAuthority createAuthority(User user) {
        return new SimpleGrantedAuthority("PATRON");
    }

}
