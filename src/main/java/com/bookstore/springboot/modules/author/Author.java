package com.bookstore.springboot.modules.author;

import com.bookstore.springboot.core.base.entity.AuditedAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * 作者实体
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "authors")
public class Author extends AuditedAggregateRoot<UUID> {

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "biography", columnDefinition = "TEXT")
    private String biography;

    @Column(name = "nationality", length = 64)
    private String nationality;
}
