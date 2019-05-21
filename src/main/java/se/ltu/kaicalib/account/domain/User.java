package se.ltu.kaicalib.account.domain;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Type(type="uuid-char")
    @Column(nullable=false, unique=true) // todo I think this is expensive to check for unique. Should assume that uuid is always unique for performance reasons. (Allthough not in this application certainly).
    final private UUID uuid = UUID.randomUUID();

    @Column
    private String email;

    @Column(unique = true)
    private String username;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String address;

    @Column
    private String password;

    @Transient
    private String passwordConfirm;

    @Column
    private int active;

    @Column(name = "patron_uuid")
    private UUID patronUuid;

/*
    //@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ManyToMany(cascade=CascadeType.ALL)
    // This joinTable is not strictly needed as ManyToMany(MappedBy = roles) in Roles entity would create the join table.
    // However, this explicitly sets the names of the columns and is more clear. todo Make concise... later.
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles; // I think it's Set as one user should only have one occurrence of a role.
*/

    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE},
        fetch=FetchType.EAGER)
    @JoinTable(
        name="user_roles",
        joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")}, // Referenced column name is to PK implicitly probably?
        inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
    )
    private Set<Role> roles;

    public User() {

    }

    // todo Remove for production. This is for autologon.
    public User(String username, String password, Set<Role> authorities) {
        this.username = username;
        this.password = password;
        this.roles = authorities;
    }

    @Override
    public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User user = (User) o;
    return username.equals(user.username);
}

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }


    @Override
    public String toString() {
        return "User{" +
            "email='" + email + '\'' +
            ", username='" + username + '\'' +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", address='" + address + '\'' +
            ", active=" + active +
            ", roles=" + roles +
            '}';
    }
}
