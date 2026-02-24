package com.bookstore.springboot;

import com.bookstore.springboot.modules.user.dto.CreateUserDto;
import com.bookstore.springboot.modules.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin")
    void should_Create_User() throws Exception {
        CreateUserDto createDto = CreateUserDto.builder()
                .username("newuser")
                .email("newuser@example.com")
                .password("secret")
                .name("New")
                .surname("User")
                .build();

        mockMvc.perform(post("/api/identity/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.email").value("newuser@example.com"));
    }

    @Test
    @WithMockUser(username = "admin")
    void should_Get_User_List() throws Exception {
        CreateUserDto user1 = CreateUserDto.builder()
                .username("user1")
                .email("user1@example.com")
                .password("pass1")
                .build();
        
        mockMvc.perform(post("/api/identity/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/identity/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.totalCount").value(1));
    }
}
