package xyz.connect.user.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "local")
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void hello() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isOk());
    }
}