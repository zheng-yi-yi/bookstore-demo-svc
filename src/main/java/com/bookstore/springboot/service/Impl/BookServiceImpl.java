package com.bookstore.springboot.service.Impl;

import com.bookstore.springboot.dto.*;
import com.bookstore.springboot.entity.Book;
import com.bookstore.springboot.exception.ResourceNotFoundException;
import com.bookstore.springboot.mapper.BookMapper;
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

    @Autowired
    private BookMapper bookMapper;

    @Override
    public BookDto createBook(CreateBookDto request) {
        Book book = bookMapper.toEntity(request);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public BookDto updateBook(UUID id, UpdateBookDto request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        bookMapper.updateEntityFromDto(request, book);

        Book updatedBook = bookRepository.save(book);
        return bookMapper.toDto(updatedBook);
    }

    @Override
    public BookDto getBookById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return bookMapper.toDto(book);
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
        return books.stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> queryBooks(BookGetListInput request) {
        return bookRepository.findAll().stream()
                .filter(b -> request.getTitle() == null || b.getTitle().toLowerCase().contains(request.getTitle().toLowerCase()))
                .filter(b -> request.getAuthor() == null || b.getAuthor().toLowerCase().contains(request.getAuthor().toLowerCase()))
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
}
