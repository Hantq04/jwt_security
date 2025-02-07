package ra.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ra.enums.ERole;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "roles")
public class Roles {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "role_Id")
    Integer roleId;

    @Column(name = "role_name")
    @Enumerated(value = EnumType.STRING)
    ERole roleName;
}
