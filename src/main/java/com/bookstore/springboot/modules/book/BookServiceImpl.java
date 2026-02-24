package com.bookstore.springboot.modules.book;

import com.bookstore.springboot.modules.book.dto.*;
import com.bookstore.springboot.modules.book.Book;
import com.bookstore.springboot.modules.book.BookService;
import com.bookstore.springboot.core.service.CrudAppService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookServiceImpl 
    extends CrudAppService<Book, BookDto, UUID, BookGetListInput, CreateBookDto, UpdateBookDto> 
    implements BookService {
}
