package se.ltu.kaicalib.account.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="Role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column
    private String role;

    //@ManyToMany(cascade=CascadeType.ALL)
/*    @JoinTable(name="user_roles",
        joinColumns={@JoinColumn(name="role_id", referencedColumnName="id")},
        inverseJoinColumns={@JoinColumn(name="user_id", referencedColumnName="id")})*/
    @ManyToMany(mappedBy = "roles") // i.e. the User owns the roles.
    private Set<User> userList; // todo Set? E.g. role will have only one user one time. Also more efficient for db.

    public Role() {
    }

    // todo Remove for production
    public Role(String role) {
        this.role = role;
    }
}
