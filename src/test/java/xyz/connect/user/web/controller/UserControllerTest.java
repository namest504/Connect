package xyz.connect.user.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import xyz.connect.user.web.model.request.CreateUserRequest;
import xyz.connect.user.web.model.request.LoginRequest;


@SpringBootTest
@AutoConfigureMockMvc
//@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        String email = "jijiji@naver.com";
        String password = "1234sda";
        CreateUserRequest createUserRequest = new CreateUserRequest(email, password);
        //when
        ResultActions resultActions = mvc.perform(post("/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        //then

    }

    @Test
    @Rollback(value = false)
    public void 로그인() throws Exception {
        //given
        String email = "jijiji@naver.com";
        String password = "1234sda";
        LoginRequest loginRequest = new LoginRequest(email, password);
        //when
        ResultActions resultActions = mvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk());

    }


}