package xyz.connect.user.web.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.connect.user.config.JwtTokenUtil;
import xyz.connect.user.web.entity.UserEntity;
import xyz.connect.user.web.model.request.CreateUserRequest;
import xyz.connect.user.web.model.request.LoginRequest;
import xyz.connect.user.web.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L; //1시간

    public void createUser(CreateUserRequest createUserRequest) {
        // password 암호화
        String hashedPassword = bCryptPasswordEncoder.encode(createUserRequest.password());

        //user 객체 생성
        UserEntity userEntity = UserEntity.builder()
                .email(createUserRequest.email())
                .password(hashedPassword)
                .build();
        //user save
        userRepository.save(userEntity);
    }

    public String loginUser(LoginRequest loginRequest) {

        // 이메일 확인
        UserEntity userEntity = userRepository.findByEmail(loginRequest.email())
                .orElseThrow();

        // 비밀번호 확인
        if (!bCryptPasswordEncoder.matches(loginRequest.password(), userEntity.getPassword())) {
            return "비밀번호가 틀렸습니다";
        }

        //이메일로 토큰 생성
        String token = JwtTokenUtil.createToken(userEntity.getEmail(), key, expireTimeMs);

        return token;

    }
}
