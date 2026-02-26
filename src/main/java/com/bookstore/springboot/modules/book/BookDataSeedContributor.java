package com.bookstore.springboot.modules.book;

import com.bookstore.springboot.core.seeding.DataSeedContext;
import com.bookstore.springboot.core.seeding.IDataSeedContributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

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
                .authorDisplay("Douglas Adams")
                .isbn("9780345391803")
                .price(BigDecimal.valueOf(42.0))
                .build());

        bookRepository.save(Book.builder()
                .title("Clean Code")
                .authorDisplay("Robert C. Martin")
                .isbn("9780132350884")
                .price(BigDecimal.valueOf(35.5))
                .build());

        bookRepository.save(Book.builder()
                .title("Design Patterns")
                .authorDisplay("Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides")
                .isbn("9780201633610")
                .price(BigDecimal.valueOf(55.0))
                .build());
    }
}

