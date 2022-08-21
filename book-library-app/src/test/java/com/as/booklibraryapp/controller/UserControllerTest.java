package com.as.booklibraryapp.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void invalidUserSubmit() throws Exception{
        this.mockMvc
                .perform(
                        post("/user/entryAfterRegistration")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                        .with(user("test").password("testpassword").roles("ADMIN", "USER"))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("user/registration-form"));
    }

    @Test
    @DirtiesContext // it will undo any database changes
    void validUserSubmit() throws Exception{
        this.mockMvc
                .perform(
                        post("/user/entryAfterRegistration")
                                .param("name", "ivana")
                                .param("surname", "ivanic")
                                .param("username", "ivana")
                                .param("email", "ivanai@mail.com")
                                .param("password", "ivana123")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .with(csrf())
                                .with(user("test").password("testpassword").roles("ADMIN", "USER"))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("/login"));
    }

    @Test
    void searchUserSubmit() throws Exception{
        this.mockMvc
                .perform(
                        post("/user/search")
                                .param("name", "Maja")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .with(csrf())
                                .with(user("test").password("testpassword").roles("ADMIN", "USER"))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("user/search"))
                .andExpect(model().attributeExists("userSet"))
                .andExpect(model().attribute("userSet", hasSize(1)));
    }
}
