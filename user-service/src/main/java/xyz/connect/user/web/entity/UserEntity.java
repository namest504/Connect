package xyz.connect.user.web.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String nickName;

    @Column(nullable = true)
    private String password;


    @Column(nullable = true)
    private String profile_image_url;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private AccountType account_type;


    @Column(nullable = true)
    private String status;

    public void updateAccountType(AccountType account_type) {
        this.account_type = account_type;
    }
}
