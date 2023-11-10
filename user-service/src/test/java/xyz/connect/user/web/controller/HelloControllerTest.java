package xyz.connect.user.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@WebMvcTest(UserController.class)
@ActiveProfiles(profiles = "test")
@Transactional
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;
    
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void hello() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isOk());
    }
}