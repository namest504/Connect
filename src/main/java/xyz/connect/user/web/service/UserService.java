package xyz.connect.user.web.service;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.connect.user.config.JwtTokenProvider;
import xyz.connect.user.exception.ErrorCode;
import xyz.connect.user.exception.UserApiException;
import xyz.connect.user.web.dto.request.CreateUserRequest;
import xyz.connect.user.web.dto.request.LoginRequest;
import xyz.connect.user.web.dto.response.LoginResponse;
import xyz.connect.user.web.entity.UserEntity;
import xyz.connect.user.web.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L; //1시간

    public String createUser(CreateUserRequest createUserRequest) {
        // email 길이 제한
        String email = createUserRequest.email();
        if (email.length() > 512) {
            throw new UserApiException(ErrorCode.INVALID_API_PARAMETER);
        }
        // email 중복 확인
        if (userRepository.findByEmail(createUserRequest.email()).isPresent()) {
            throw new UserApiException(ErrorCode.CONFLICT);
        }
        // password 길이 제한
        String password = createUserRequest.password();
        if (password.length() > 32) {
            throw new UserApiException(ErrorCode.INVALID_API_PARAMETER);
        }
        // password 암호화
        String hashedPassword = bCryptPasswordEncoder.encode(password);

        // user 객체 생성
        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .password(hashedPassword)
                .profile_image_url(createUserRequest.profile_image_url())
                .build();

        // user save
        userRepository.save(userEntity);
        log.info("User 생성 완료 ={}", userEntity);
        return userEntity.getEmail();
    }

    public LoginResponse loginUser(LoginRequest loginRequest) {

        // 이메일 확인
        UserEntity userEntity = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new UserApiException(ErrorCode.NO_THAT_USER));
        // 비밀번호 확인
        if (!bCryptPasswordEncoder.matches(loginRequest.password(), userEntity.getPassword())) {
            throw new UserApiException(ErrorCode.INVALID_API_PARAMETER);
        }
        // 토큰 생성
        String AccessToken = jwtTokenProvider.createAccssToken(userEntity.getUserID(),
                userEntity.getEmail(),
                key,
                expireTimeMs);
        String RefreshToken = jwtTokenProvider.createRefreshToken(userEntity.getUserID(),
                userEntity.getEmail(),
                key,
                expireTimeMs);
        log.info("Access token ={} ", AccessToken);
        log.info("Refresh token = {}", RefreshToken);
        LoginResponse loginResponse = new LoginResponse(AccessToken, RefreshToken);
        return loginResponse;

    }

    public Boolean checkEmail(String email) {
        // 이메일 확인
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        return !userEntity.isPresent();
    }
}
