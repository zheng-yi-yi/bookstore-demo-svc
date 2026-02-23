package com.bookstore.springboot.controller;

import com.bookstore.springboot.dto.*;
import com.bookstore.springboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody CreateBookDto request) {
        return bookService.createBook(request);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable UUID id, @RequestBody UpdateBookDto request) {
        return bookService.updateBook(id, request);
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable UUID id) {
        return bookService.getBookById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/search")
    public List<BookDto> queryBooks(BookGetListInput request) {
        return bookService.queryBooks(request);
    }
}
