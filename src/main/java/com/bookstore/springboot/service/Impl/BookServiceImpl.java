package com.bookstore.springboot.service.Impl;

import com.bookstore.springboot.dto.*;
import com.bookstore.springboot.entity.Book;
import com.bookstore.springboot.service.BookService;
import com.bookstore.springboot.service.base.CrudAppService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookServiceImpl 
    extends CrudAppService<Book, BookDto, UUID, BookGetListInput, CreateBookDto, UpdateBookDto> 
    implements BookService {
}
