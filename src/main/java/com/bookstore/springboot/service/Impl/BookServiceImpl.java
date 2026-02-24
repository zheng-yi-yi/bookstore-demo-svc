package com.bookstore.springboot.service.Impl;

import com.bookstore.springboot.dto.*;
import com.bookstore.springboot.entity.Book;
import com.bookstore.springboot.mapper.BookMapper;
import com.bookstore.springboot.service.BookService;
import com.bookstore.springboot.service.base.CrudAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl 
    extends CrudAppService<Book, BookDto, UUID, BookGetListInput, CreateBookDto, UpdateBookDto> 
    implements BookService {

    @Override
    public List<BookDto> getList(BookGetListInput request) {
        if (request.getTitle() == null && request.getAuthor() == null) {
            return super.getList(request);
        }
        return repository.findAll().stream()
                .filter(b -> request.getTitle() == null || b.getTitle().toLowerCase().contains(request.getTitle().toLowerCase()))
                .filter(b -> request.getAuthor() == null || b.getAuthor().toLowerCase().contains(request.getAuthor().toLowerCase()))
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
