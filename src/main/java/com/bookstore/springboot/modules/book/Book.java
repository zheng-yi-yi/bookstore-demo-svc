package com.bookstore.springboot.modules.book;

import com.bookstore.springboot.core.base.entity.AuditedAggregateRoot;
import com.bookstore.springboot.modules.author.Author;
import com.bookstore.springboot.modules.category.Category;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 图书实体（核心商品）
 * 继承 AuditedAggregateRoot<UUID>，自带创建/修改时间、创建/修改人、版本号、逻辑删除等审计字段
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "books",
        indexes = {
                @Index(name = "idx_isbn", columnList = "isbn", unique = true),
                @Index(name = "idx_title", columnList = "title"),
                @Index(name = "idx_publish_date", columnList = "publish_date")
        })
public class Book extends AuditedAggregateRoot<UUID> {

    /**
     * 图书标题（必填）
     */
    @Column(name = "title", nullable = false, length = 512)
    private String title;

    /**
     * 多作者模式（多对多关联）
     */
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "book_authors",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors = new ArrayList<>();

    /**
     * 针对部分场景或搜索优化的作者冗余展示（可选）
     */
    @Column(name = "author_display", length = 512)
    private String authorDisplay;

    /**
     * ISBN（国际标准书号，唯一索引，必填）
     */
    @Column(name = "isbn", unique = true, nullable = false, length = 13)
    private String isbn;

    /**
     * 图书副标题
     */
    @Column(name = "subtitle", length = 512)
    private String subtitle;

    /**
     * 图书描述 / 内容简介（富文本）
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * 销售价
     */
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * 原价
     */
    @Column(name = "original_price", precision = 10, scale = 2)
    private BigDecimal originalPrice;

    /**
     * 库存数量
     */
    @Builder.Default
    @Column(name = "stock", nullable = false)
    private Integer stock = 0;

    /**
     * 出版社
     */
    @Column(name = "publisher", length = 128)
    private String publisher;

    /**
     * 出版日期
     */
    @Column(name = "publish_date")
    private LocalDate publishDate;

    /**
     * 页数
     */
    @Column(name = "page_count")
    private Integer pageCount;

    /**
     * 装帧
     */
    @Column(name = "binding", length = 32)
    private String binding;

    /**
     * 图书语言
     */
    @Builder.Default
    @Column(name = "language", length = 32)
    private String language = "中文";

    /**
     * 图书封面图片URL
     */
    @Column(name = "cover_image_url", length = 1024)
    private String coverImageUrl;

    /**
     * 平均评分
     */
    @Builder.Default
    @Column(name = "average_rating", precision = 3, scale = 1)
    private BigDecimal averageRating = BigDecimal.ZERO;

    /**
     * 评价数量
     */
    @Builder.Default
    @Column(name = "rating_count", nullable = false)
    private Integer ratingCount = 0;

    /**
     * 关联主分类（单分类模式，或后续扩展到多分类）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * 冗余存储的全路径分类名称，供快速检索
     */
    @Column(name = "category_path", length = 512)
    private String categoryPath;

    /**
     * 销售量
     */
    @Builder.Default
    @Column(name = "sales_volume", nullable = false)
    private Long salesVolume = 0L;

    /**
     * 是否上架
     */
    @Builder.Default
    @Column(name = "is_published", nullable = false)
    private Boolean isPublished = true;

    /**
     * 辅助方法：判断是否有库存
     */
    public boolean hasStock() {
        return stock != null && stock > 0;
    }

    /**
     * 扣减库存
     */
    public void decreaseStock(int quantity) {
        if (quantity <= 0) return;
        if (this.stock < quantity) {
            throw new IllegalStateException("库存不足");
        }
        this.stock -= quantity;
    }
}

