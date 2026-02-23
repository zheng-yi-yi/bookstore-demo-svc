package com.bookstore.springboot.service.Impl;

import com.bookstore.springboot.dto.*;
import com.bookstore.springboot.entity.Book;
import com.bookstore.springboot.exception.ResourceNotFoundException;
import com.bookstore.springboot.repository.BookRepository;
import com.bookstore.springboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookDto createBook(CreateBookDto request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .price(request.getPrice())
                .build();
        Book savedBook = bookRepository.save(book);
        return mapToResponse(savedBook);
    }

    @Override
    public BookDto updateBook(UUID id, UpdateBookDto request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        if (request.getTitle() != null) book.setTitle(request.getTitle());
        if (request.getAuthor() != null) book.setAuthor(request.getAuthor());
        if (request.getPrice() != null) book.setPrice(request.getPrice());

        Book updatedBook = bookRepository.save(book);
        return mapToResponse(updatedBook);
    }

    @Override
    public BookDto getBookById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return mapToResponse(book);
    }

    @Override
    public void deleteBook(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        bookRepository.delete(book);
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> queryBooks(BookGetListInput request) {
        return bookRepository.findAll().stream()
                .filter(b -> request.getTitle() == null || b.getTitle().toLowerCase().contains(request.getTitle().toLowerCase()))
                .filter(b -> request.getAuthor() == null || b.getAuthor().toLowerCase().contains(request.getAuthor().toLowerCase()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private BookDto mapToResponse(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .build();
    }
}
