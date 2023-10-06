package xyz.connect.user.web.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity(name = "ACCOUNT")
@Data
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column( nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column( nullable = true)
    private String profile_image_url;

    @Column( nullable = true)
    private String account_type;

    @Column( nullable = true)
    private String status;


}
