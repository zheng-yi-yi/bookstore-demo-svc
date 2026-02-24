package com.bookstore.springboot.modules.book;

import com.bookstore.springboot.modules.book.dto.*;
import com.bookstore.springboot.core.service.ICrudAppService;

import java.util.UUID;

public interface BookService extends ICrudAppService<BookDto, UUID, BookGetListInput, CreateBookDto, UpdateBookDto> {
}
