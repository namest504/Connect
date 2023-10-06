package xyz.connect.user.web.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.connect.user.web.entity.UserEntity;
import xyz.connect.user.web.model.request.CreateUserRequest;
import xyz.connect.user.web.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;


    public void createUser(CreateUserRequest createUserRequest) {

        //user 객체 생성
        UserEntity userEntity = UserEntity.builder()
                .email(createUserRequest.email())
                // TODO: Password Encoder 적용 필요
                .password(createUserRequest.password())
                .build();
        //user save
        userRepository.save(userEntity);
    }
}
