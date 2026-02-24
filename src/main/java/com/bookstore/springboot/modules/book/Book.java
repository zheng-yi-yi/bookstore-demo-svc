package com.bookstore.springboot.modules.book;

import com.bookstore.springboot.core.entity.AuditedAggregateRoot;
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
@Table(name = "books")
public class Book extends AuditedAggregateRoot<UUID> {
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "price", nullable = false)
    private double price;
}
