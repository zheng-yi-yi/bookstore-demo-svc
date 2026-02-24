package com.bookstore.springboot.modules.user.dto;

import com.bookstore.springboot.core.dto.base.AuditedEntityDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserDto extends AuditedEntityDto<UUID> {
    private String username;
    private String email;
    private String name;
    private String surname;
}
