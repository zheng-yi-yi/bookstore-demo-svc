package com.bookstore.springboot.mapper;

import com.bookstore.springboot.dto.BookDto;
import com.bookstore.springboot.dto.CreateBookDto;
import com.bookstore.springboot.dto.UpdateBookDto;
import com.bookstore.springboot.entity.Book;
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
