package com.bookstore.springboot.service;

import com.bookstore.springboot.dto.*;

import java.util.List;
import java.util.UUID;

public interface BookService {
    BookDto createBook(CreateBookDto request);

    BookDto updateBook(UUID id, UpdateBookDto request);

    BookDto getBookById(UUID id);

    void deleteBook(UUID id);

    List<BookDto> getAllBooks();

    List<BookDto> queryBooks(BookGetListInput request);
}
