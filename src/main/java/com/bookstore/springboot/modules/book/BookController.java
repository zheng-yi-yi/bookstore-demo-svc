package com.bookstore.springboot.modules.book;

import com.bookstore.springboot.core.base.controller.CrudController;
import com.bookstore.springboot.modules.book.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController extends CrudController<BookDto, UUID, BookGetListInput, CreateBookDto, UpdateBookDto> {
}

