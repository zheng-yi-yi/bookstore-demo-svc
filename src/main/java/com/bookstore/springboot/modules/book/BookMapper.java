package com.bookstore.springboot.modules.book;

import com.bookstore.springboot.core.base.mapper.BaseMapper;
import com.bookstore.springboot.core.base.mapper.IgnoreAuditedProperties;
import com.bookstore.springboot.modules.book.dto.BookDto;
import com.bookstore.springboot.modules.book.dto.CreateBookDto;
import com.bookstore.springboot.modules.book.dto.UpdateBookDto;
import com.bookstore.springboot.modules.book.Book;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper extends BaseMapper<Book, BookDto, CreateBookDto, UpdateBookDto> {
    
    @Override
    BookDto toDto(Book book);

    @Override
    @IgnoreAuditedProperties
    Book toEntity(CreateBookDto dto);

    @Override
    @IgnoreAuditedProperties
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateBookDto dto, @MappingTarget Book book);
}

