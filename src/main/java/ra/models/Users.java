package ra.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Integer userId;

    @Column(name = "user_name", unique = true, nullable = false)
    String userName;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "created")
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date created;

    @Column(name = "email", unique = true, nullable = false)
    String email;

    @Column(name = "phone")
    String phone;

    @Column(name = "user_status")
    boolean userStatus;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role"
            , joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Roles> listRoles = new HashSet<>();
}
