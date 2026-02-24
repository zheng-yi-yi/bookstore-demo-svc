package com.bookstore.springboot.data;

import com.bookstore.springboot.entity.Book;
import com.bookstore.springboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class BookDataSeedContributor implements IDataSeedContributor {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void seed(DataSeedContext context) {
        if (bookRepository.count() > 0) {
            return;
        }

        bookRepository.save(Book.builder()
                .title("The Hitchhiker's Guide to the Galaxy")
                .author("Douglas Adams")
                .price(42.0)
                .build());

        bookRepository.save(Book.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .price(35.5)
                .build());

        bookRepository.save(Book.builder()
                .title("Design Patterns")
                .author("Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides")
                .price(55.0)
                .build());
    }
}
