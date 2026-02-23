package com.bookstore.springboot.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class AuditedAggregateRoot<TKey> extends Entity<TKey> {
    @Column(name = "creation_time", nullable = false, updatable = false)
    private LocalDateTime creationTime;

    @Column(name = "creator_id")
    private UUID creatorId;

    @Column(name = "last_modification_time")
    private LocalDateTime lastModificationTime;

    @Column(name = "last_modifier_id")
    private UUID lastModifierId;
}
