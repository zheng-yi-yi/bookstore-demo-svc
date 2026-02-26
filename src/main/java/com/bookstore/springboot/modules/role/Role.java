package com.bookstore.springboot.modules.role;

import com.bookstore.springboot.core.base.entity.AuditedAggregateRoot;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "roles")
public class Role extends AuditedAggregateRoot<UUID> {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_static")
    private boolean staticRole;
}

