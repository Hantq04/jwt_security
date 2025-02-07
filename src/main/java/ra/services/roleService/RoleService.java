package ra.services.roleService;

import ra.enums.ERole;
import ra.models.Roles;

import java.util.Optional;

public interface RoleService {
    Optional<Roles> findByRoleName(ERole roleName);
}
