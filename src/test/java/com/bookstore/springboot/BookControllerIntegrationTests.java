package com.bookstore.springboot;

import com.bookstore.springboot.dto.BookDto;
import com.bookstore.springboot.dto.CreateBookDto;
import com.bookstore.springboot.dto.UpdateBookDto;
import com.bookstore.springboot.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void should_Create_Book() throws Exception {
        CreateBookDto createDto = new CreateBookDto();
        createDto.setTitle("Test Book");
        createDto.setAuthor("Test Author");
        createDto.setPrice(29.99);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"))
                .andExpect(jsonPath("$.price").value(29.99));
    }

    @Test
    void should_Get_Book_By_Id() throws Exception {
        CreateBookDto createDto = new CreateBookDto();
        createDto.setTitle("Test Book");
        createDto.setAuthor("Test Author");
        createDto.setPrice(19.99);

        String response = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
                .andReturn().getResponse().getContentAsString();
        
        BookDto createdBook = objectMapper.readValue(response, BookDto.class);
        UUID id = createdBook.getId();

        mockMvc.perform(get("/api/books/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    void should_Return_404_When_Book_Not_Found() throws Exception {
        mockMvc.perform(get("/api/books/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString("Entity not found")));
    }

    @Test
    void should_Update_Book() throws Exception {
        CreateBookDto createDto = new CreateBookDto();
        createDto.setTitle("Original Title");
        createDto.setAuthor("Author");
        createDto.setPrice(10.0);

        String response = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
                .andReturn().getResponse().getContentAsString();
        
        BookDto createdBook = objectMapper.readValue(response, BookDto.class);
        UUID id = createdBook.getId();

        UpdateBookDto updateDto = new UpdateBookDto();
        updateDto.setTitle("Updated Title");
        updateDto.setPrice(15.0);

        mockMvc.perform(put("/api/books/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.author").value("Author")) // Should remain the same
                .andExpect(jsonPath("$.price").value(15.0));
    }

    @Test
    void should_Delete_Book() throws Exception {
        CreateBookDto createDto = new CreateBookDto();
        createDto.setTitle("Delete Me");
        createDto.setAuthor("Author");
        createDto.setPrice(10.0);

        String response = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
                .andReturn().getResponse().getContentAsString();
        
        BookDto createdBook = objectMapper.readValue(response, BookDto.class);
        UUID id = createdBook.getId();

        mockMvc.perform(delete("/api/books/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/books/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_Get_List_With_Paging_And_Sorting() throws Exception {
        // Create 15 books
        for (int i = 0; i < 15; i++) {
            CreateBookDto createDto = new CreateBookDto();
            createDto.setTitle("Book " + String.format("%02d", i));
            createDto.setAuthor("Author " + (i % 3));
            createDto.setPrice(10.0 + i);
            mockMvc.perform(post("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createDto)));
        }

        // Test paging: skip 10, max 10
        mockMvc.perform(get("/api/books")
                .param("skipCount", "10")
                .param("maxResultCount", "10")
                .param("sorting", "title asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").value(15))
                .andExpect(jsonPath("$.items", hasSize(5)))
                .andExpect(jsonPath("$.items[0].title").value("Book 10"));

        // Test sorting: title desc
        mockMvc.perform(get("/api/books")
                .param("skipCount", "0")
                .param("maxResultCount", "5")
                .param("sorting", "title desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].title").value("Book 14"));
    }

    @Test
    void should_Filter_By_Title_And_Author() throws Exception {
        // Create books with specific titles and authors
        createBook("Java Programming", "John Doe", 30.0);
        createBook("Python Basics", "Jane Smith", 25.0);
        createBook("Spring Boot in Action", "John Doe", 40.0);

        // Filter by title (LIKE)
        mockMvc.perform(get("/api/books")
                .param("title", "Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").value(1))
                .andExpect(jsonPath("$.items[0].title").value("Java Programming"));

        // Filter by author (LIKE)
        mockMvc.perform(get("/api/books")
                .param("author", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").value(2))
                .andExpect(jsonPath("$.items[*].author", everyItem(containsString("John"))));

        // Filter by both
        mockMvc.perform(get("/api/books")
                .param("title", "Spring")
                .param("author", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").value(1))
                .andExpect(jsonPath("$.items[0].title").value("Spring Boot in Action"));
    }

    private void createBook(String title, String author, double price) throws Exception {
        CreateBookDto createDto = new CreateBookDto();
        createDto.setTitle(title);
        createDto.setAuthor(author);
        createDto.setPrice(price);
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)));
    }
}
