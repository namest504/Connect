package xyz.connect.user.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import xyz.connect.user.config.JwtTokenProvider;
import xyz.connect.user.config.SecurityConfig;
import xyz.connect.user.web.dto.request.CreateUserRequest;
import xyz.connect.user.web.dto.request.LoginRequest;
import xyz.connect.user.web.service.KafkaProducerService;
import xyz.connect.user.web.service.UserService;


@WebMvcTest(UserController.class)
@ActiveProfiles(profiles = "test")
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
class UserControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private KafkaProducerService kafkaProducerService;


    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void 회원가입() throws Exception {
        //given
        String email = "fddfdassa@naver.com";
        String password = "1234sda";
        String nickName = "suhoon";
        CreateUserRequest createUserRequest = new CreateUserRequest(email, nickName, password, null);

        String body = objectMapper.writeValueAsString(createUserRequest);
        BDDMockito.doNothing().when(kafkaProducerService).sendMessage(any());
        //when
        mvc.perform(
                        post("/sign-up")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                .andExpect(status().is(200));

        //then

    }

    @Test
    public void 로그인() throws Exception {
        //given
        String email = "fdsa@naver.com";
        String password = "1234sda";

        LoginRequest loginRequest = new LoginRequest(email, password);
        //when
        mvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().is(200));

        // then

    }


}