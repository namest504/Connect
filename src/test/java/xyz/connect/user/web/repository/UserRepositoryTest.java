package xyz.connect.user.web.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import xyz.connect.user.web.entity.UserEntity;


@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
        //when
        //then

    }

    @Test
    void findByNickName() {
        //given
        //when
        //then
    }

    @Test
    void findByEmailAndAccount_type() {
        //given
        //when
        //then
    }
}