package ma.fstg.auth.init;

import lombok.RequiredArgsConstructor;
import ma.fstg.auth.entities.AccountEntity;
import ma.fstg.auth.entities.PermissionEntity;
import ma.fstg.auth.repositories.AccountRepository;
import ma.fstg.auth.repositories.PermissionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Création des rôles s'ils n'existent pas
        if (permissionRepository.count() == 0) {
            PermissionEntity adminRole = new PermissionEntity("ADMIN", "Administrateur avec tous les droits");
            PermissionEntity userRole = new PermissionEntity("USER", "Utilisateur standard");
            PermissionEntity managerRole = new PermissionEntity("MANAGER", "Gestionnaire avec droits avancés");

            permissionRepository.saveAll(List.of(adminRole, userRole, managerRole));
            System.out.println("Rôles créés avec succès!");
        }

        // Création du compte admin par défaut
        if (!accountRepository.existsByUsername("malak_admin")) {
            AccountEntity adminAccount = new AccountEntity();
            adminAccount.setUsername("malak_admin");
            adminAccount.setEncodedPassword(passwordEncoder.encode("Admin123!"));
            adminAccount.setEmail("malak@example.com");
            adminAccount.setIsActive(true);
            adminAccount.setIsAccountNonLocked(true);
            adminAccount.setIsCredentialsNonExpired(true);
            adminAccount.setIsAccountNonExpired(true);

            PermissionEntity adminRole = permissionRepository.findByRoleName("ADMIN").orElseThrow();
            adminAccount.setRoles(List.of(adminRole));

            accountRepository.save(adminAccount);
            System.out.println("Compte admin créé: malak_admin / Admin123!");
        }

        // Création d'un compte utilisateur standard
        if (!accountRepository.existsByUsername("malak_user")) {
            AccountEntity userAccount = new AccountEntity();
            userAccount.setUsername("malak_user");
            userAccount.setEncodedPassword(passwordEncoder.encode("User123!"));
            userAccount.setEmail("user@example.com");
            userAccount.setIsActive(true);
            userAccount.setIsAccountNonLocked(true);
            userAccount.setIsCredentialsNonExpired(true);
            userAccount.setIsAccountNonExpired(true);

            PermissionEntity userRole = permissionRepository.findByRoleName("USER").orElseThrow();
            userAccount.setRoles(List.of(userRole));

            accountRepository.save(userAccount);
            System.out.println("Compte utilisateur créé: malak_user / User123!");
        }
    }
}