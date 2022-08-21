package com.as.booklibraryapp.controller.rest;

import com.as.booklibraryapp.domain.Users;
import com.as.booklibraryapp.repository.JpaUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JpaUserRepository userRepository;

    @Test
    void getAllUsers() throws Exception{
        this.mockMvc
                .perform(
                        get("/api/user")
                                .with(csrf())
                                .with(user("test").password("testpassword").roles("ADMIN", "USER"))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @DirtiesContext
    void saveUser() throws Exception{
        String name = "marina";
        String surname = "rinic";
        String username = "marina";
        String email = "marinar@mail.com";
        String password = "marina123";

        Users user = new Users();
        user.setName(name);
        user.setSurname(surname);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        this.mockMvc
                .perform(
                        post("/api/user")
                                .with(csrf())
                                .with(user("test").password("testpassword").roles("ADMIN", "USER"))
                                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.surname").value(surname))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.password").value(password));
    }

    @Test
    @DirtiesContext
    void deleteUser() throws Exception{
        final long id = 1;

        assertTrue(userRepository.findById(id).isPresent());

        this.mockMvc
                .perform(
                        delete("/api/user/" + id)
                                .with(csrf())
                                .with(user("test").password("testpassword").roles("ADMIN", "USER"))
                )
                .andExpect(status().isNoContent());

        assertTrue(userRepository.findById(id).isEmpty());
    }

    @Test
    void deleteUserWithoutAdminRights() throws Exception{
        final long id = 1L;

        this.mockMvc
                .perform(
                        delete("/api/user/" + id)
                                .with(csrf())
                                .with(user("test").password("testpassword").roles("USER"))
                )
                .andExpect(status().isForbidden());
    }
}
