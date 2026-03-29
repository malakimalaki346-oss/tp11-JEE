package ma.fstg.auth.repositories;

import ma.fstg.auth.entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

    Optional<PermissionEntity> findByRoleName(String roleName);

    boolean existsByRoleName(String roleName);
}