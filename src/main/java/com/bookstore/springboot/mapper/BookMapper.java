package com.bookstore.springboot.mapper;

import com.bookstore.springboot.dto.BookDto;
import com.bookstore.springboot.dto.CreateBookDto;
import com.bookstore.springboot.dto.UpdateBookDto;
import com.bookstore.springboot.entity.Book;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {
    
    BookDto toDto(Book book);

    @IgnoreAuditedProperties
    Book toEntity(CreateBookDto dto);

    @IgnoreAuditedProperties
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateBookDto dto, @MappingTarget Book book);
}
