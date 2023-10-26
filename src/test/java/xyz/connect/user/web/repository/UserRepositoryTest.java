package xyz.connect.user.web.repository;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import xyz.connect.user.web.entity.AccountType;
import xyz.connect.user.web.entity.UserEntity;


@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void db가_잘연결되어있다() {
        //given
        UserEntity userEntity = UserEntity.builder()
                .id(10L)
                .email("suhoon@naver.com")
                .build();

        //when
        UserEntity user = userRepository.save(userEntity);

        //then
        Assertions.assertThat(user.getEmail()).isEqualTo("suhoon@naver.com");
    }

    @Test
    void findByEmail_로User를검색할수있다() {
        //given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .email("suhoon@naver.com")
                .build();

        //when
        UserEntity user = userRepository.save(userEntity);
        Optional<UserEntity> result = userRepository.findByEmail("suhoon@naver.com");
        //then
        Assertions.assertThat(result.get().getEmail()).isEqualTo("suhoon@naver.com");

    }

    @Test
    void findByNickName() {
        //given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .email("suhoon@naver.com")
                .nickName("suhoon")
                .build();

        //when
        UserEntity user = userRepository.save(userEntity);
        Optional<UserEntity> result = userRepository.findByNickName("suhoon");
        //then
        Assertions.assertThat(result.get().getEmail()).isEqualTo("suhoon@naver.com");
    }

    @Test
    void findByEmailAndAccount_type() {
        //given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .email("suhoon@naver.com")
                .nickName("suhoon")
                .account_type(AccountType.UNCHEKED)
                .build();

        //when
        UserEntity user = userRepository.save(userEntity);
        Optional<UserEntity> result = userRepository.findByEmailAndAccount_type("suhoon@naver.com",
                AccountType.UNCHEKED);
        //then
        Assertions.assertThat(result.get().getEmail()).isEqualTo("suhoon@naver.com");
    }
}