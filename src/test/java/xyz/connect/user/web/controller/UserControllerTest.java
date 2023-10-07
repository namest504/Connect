package xyz.connect.user.web.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import xyz.connect.user.web.model.request.CreateUserRequest;
import xyz.connect.user.web.model.request.LoginRequest;
import xyz.connect.user.web.repository.UserRepository;


@SpringBootTest
@AutoConfigureMockMvc
//@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

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
        // 테스트에서 resultActions를 얻은 후
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        // 응답 본문(body)을 가져옴
        String responseBody = response.getContentAsString();
        // responseBody가 비어있지 않은지 확인
        assertTrue(!responseBody.isEmpty());
    }


}