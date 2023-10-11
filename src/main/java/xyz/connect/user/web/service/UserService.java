package xyz.connect.user.web.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.connect.user.config.JwtTokenUtil;
import xyz.connect.user.exception.ErrorCode;
import xyz.connect.user.exception.UserApiException;
import xyz.connect.user.web.dto.request.CreateUserRequest;
import xyz.connect.user.web.dto.request.LoginRequest;
import xyz.connect.user.web.entity.UserEntity;
import xyz.connect.user.web.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L; //1시간

    public void createUser(CreateUserRequest createUserRequest) {
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

        //user 객체 생성
        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .password(hashedPassword)
                .build();
        //user save
        userRepository.save(userEntity);
    }

    public String loginUser(LoginRequest loginRequest) {

        // 이메일 확인
        UserEntity userEntity = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new UserApiException(ErrorCode.INVALID_API_PARAMETER));

        // 비밀번호 확인
        if (!bCryptPasswordEncoder.matches(loginRequest.password(), userEntity.getPassword())) {
            throw new UserApiException(ErrorCode.INVALID_API_PARAMETER);
        }

        //이메일로 토큰 생성
        String token = JwtTokenUtil.createToken(userEntity.getUserID(), userEntity.getEmail(), key,
                expireTimeMs);

        log.info("token ={} ", token);
        return token;

    }
}
