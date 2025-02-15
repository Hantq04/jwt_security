package ra.services.roleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ra.enums.ERole;
import ra.models.Roles;
import ra.repositories.RoleRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepo roleRepo;

    @Override
    public Optional<Roles> findByRoleName(ERole roleName) {
        return roleRepo.findByRoleName(roleName);
    }
}
