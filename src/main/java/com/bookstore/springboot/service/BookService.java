package com.bookstore.springboot.service;

import com.bookstore.springboot.dto.*;

import java.util.List;

public interface BookService {
    BookDto createBook(CreateBookDto request);

    BookDto updateBook(Long id, UpdateBookDto request);

    BookDto getBookById(Long id);

    void deleteBook(Long id);

    List<BookDto> getAllBooks();

    List<BookDto> queryBooks(BookGetListInput request);
}
