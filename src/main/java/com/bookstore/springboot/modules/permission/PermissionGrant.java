package com.bookstore.springboot.modules.permission;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "permission_grants")
public class PermissionGrant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "provider_name", nullable = false)
    private String providerName; // e.g., "R" for Role, "U" for User

    @Column(name = "provider_key", nullable = false)
    private String providerKey; // e.g., Role Name or User Id
}
