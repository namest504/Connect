package xyz.connect.user.web.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.connect.user.web.entity.UserEntity;
import xyz.connect.user.web.model.request.CreateUserRequest;
import xyz.connect.user.web.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


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
}
