package com.bookstore.springboot.core.base.mapper;

import org.mapstruct.Mapping;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
@Mapping(target = "id", ignore = true)
@Mapping(target = "creationTime", ignore = true)
@Mapping(target = "creatorId", ignore = true)
@Mapping(target = "lastModificationTime", ignore = true)
@Mapping(target = "lastModifierId", ignore = true)
public @interface IgnoreAuditedProperties {
}

