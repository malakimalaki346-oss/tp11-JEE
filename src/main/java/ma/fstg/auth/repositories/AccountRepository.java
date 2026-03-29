package ma.fstg.auth.repositories;

import ma.fstg.auth.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByUsername(String username);

    Optional<AccountEntity> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT a FROM AccountEntity a LEFT JOIN FETCH a.roles WHERE a.username = :username")
    Optional<AccountEntity> findAccountWithRolesByUsername(@Param("username") String username);
}