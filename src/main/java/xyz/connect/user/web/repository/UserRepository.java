package xyz.connect.user.web.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.connect.user.web.entity.AccountType;
import xyz.connect.user.web.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByNickName(String nickName);

    @Query("select u from UserEntity u where u.email = :email and u.account_type = :accountType")
    Optional<UserEntity> findByEmailAndAccount_type(@Param("email") String email,
            @Param("accountType") AccountType accountType);
}
