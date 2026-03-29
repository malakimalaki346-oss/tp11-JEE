package ma.fstg.auth.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String roleName;

    @Column(length = 100)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private List<AccountEntity> accounts = new ArrayList<>();

    public PermissionEntity(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }
}