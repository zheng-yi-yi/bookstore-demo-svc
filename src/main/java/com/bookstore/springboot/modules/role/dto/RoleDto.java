package com.bookstore.springboot.modules.role.dto;

import com.bookstore.springboot.core.dto.base.AuditedEntityDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class RoleDto extends AuditedEntityDto<UUID> {
    private String name;
    private String description;
    private boolean staticRole;
}
