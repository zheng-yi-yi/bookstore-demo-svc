package com.bookstore.springboot.core.dto.base;

import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public abstract class AuditedEntityDto<TKey> extends EntityDto<TKey> {
    private LocalDateTime creationTime;
    private UUID creatorId;
    private LocalDateTime lastModificationTime;
    private UUID lastModifierId;
}
