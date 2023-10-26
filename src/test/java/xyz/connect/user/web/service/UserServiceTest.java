package xyz.connect.user.web.service;

import static org.mockito.ArgumentMatchers.any;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;
import xyz.connect.user.config.JwtTokenProvider;
import xyz.connect.user.web.dto.request.CreateUserRequest;
import xyz.connect.user.web.dto.request.LoginRequest;
import xyz.connect.user.web.dto.response.LoginResponse;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
})
public class UserServiceTest {


    @Autowired
    private UserService userService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private KafkaProducerService kafkaProducerService;


    @Test
    void createUser_로User를만들수있다() {
        //given
        String email = "susuhoon@naver.com";
        String nickname = "suhooosn";
        String password = "1234";
        String profile_image_url = "fdsa.com";
        String account_type = "UNCHECKED";
        String status = "OFFLINE";
        CreateUserRequest createUserRequest = new CreateUserRequest(email, nickname, password, profile_image_url);
        BDDMockito.doNothing().when(kafkaProducerService).sendMessage(any());

        //when
        String createdUserEmail = userService.createUser(createUserRequest);

        //then
        Assertions.assertThat(createdUserEmail).isEqualTo("susuhoon@naver.com");

    }

    @Test
    void loginUser_로로그인을할수있다() {

        //given
        String email = "suhoon@naver.com";
        String password = "1234";
        LoginRequest loginRequest = new LoginRequest(email, password);
        BDDMockito.given(bCryptPasswordEncoder.matches(any(), any())).willReturn(true);
        //when
        LoginResponse loginResponse = userService.loginUser(loginRequest);
        //then
        Assertions.assertThat(loginResponse.AccessToken()).isNotEmpty();

    }

    @Test
    void checkEmail_로이메일중복확인을할수있다() {
        //given
        String email = "suhoon@naver.com";
        //when
        boolean is_true = userService.checkEmail(email);
        //then
        Assertions.assertThat(is_true).isFalse();


    }

    @Test
    void confirmAuthMail_로이메일인증확인을할수있다() {
    }

    @Test
    void checkNickName_로닉네임중복확인을할수있다() {
        //given
        String nickname = "suhoon";
        //when
        boolean is_true = userService.checkNickName(nickname);
        //then
        Assertions.assertThat(is_true).isFalse();
    }
}