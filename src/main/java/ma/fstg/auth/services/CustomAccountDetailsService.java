package ma.fstg.auth.services;

import lombok.RequiredArgsConstructor;
import ma.fstg.auth.entities.AccountEntity;
import ma.fstg.auth.entities.PermissionEntity;
import ma.fstg.auth.repositories.AccountRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomAccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity account = accountRepository.findAccountWithRolesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));

        if (!account.getIsActive()) {
            throw new UsernameNotFoundException("Compte désactivé: " + username);
        }

        return new User(
                account.getUsername(),
                account.getEncodedPassword(),
                account.getIsAccountNonExpired(),
                true, // credentials non-expired
                account.getIsCredentialsNonExpired(),
                account.getIsAccountNonLocked(),
                mapRolesToAuthorities(account.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<PermissionEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toSet());
    }
}