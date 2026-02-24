package com.bookstore.springboot.service;

import com.bookstore.springboot.dto.*;
import com.bookstore.springboot.service.base.ICrudAppService;

import java.util.UUID;

public interface BookService extends ICrudAppService<BookDto, UUID, BookGetListInput, CreateBookDto, UpdateBookDto> {
}
