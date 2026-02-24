package com.bookstore.springboot.controller;

import com.bookstore.springboot.controller.base.CrudController;
import com.bookstore.springboot.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController extends CrudController<BookDto, UUID, BookGetListInput, CreateBookDto, UpdateBookDto> {
}
