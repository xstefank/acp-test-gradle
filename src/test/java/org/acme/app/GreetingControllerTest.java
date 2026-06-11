package org.acme.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void listIsEmptyInitially() throws Exception {
        mockMvc.perform(get("/greetings"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void createAndRetrieveGreeting() throws Exception {
        String location = mockMvc.perform(post("/greetings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"message": "Hello Quarkus"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.message", is("Hello Quarkus")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        mockMvc.perform(get("/greetings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("Hello Quarkus")));
    }

    @Test
    void getByIdReturnsNotFoundForMissing() throws Exception {
        mockMvc.perform(get("/greetings/9999"))
                .andExpect(status().isNotFound());
    }
}
