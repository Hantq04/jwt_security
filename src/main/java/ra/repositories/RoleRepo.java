package ra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.enums.ERole;
import ra.models.Roles;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByRoleName(ERole roleName);
}
