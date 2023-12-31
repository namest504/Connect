package xyz.connect.user.web.dto.params;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KaKaoInfoParam implements OAuthInfoParam {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;


    @Override
    public String getEmail() {

        return kakaoAccount.email;
    }

    @Override
    public String getNickname() {
        return kakaoAccount.profile.nickname;
    }

    @Override
    public String getImage() {
        return kakaoAccount.profile.profile_image_url;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {

        private KakaoProfile profile;
        private String email;
    }


    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoProfile {

        private String nickname;
        private String profile_image_url;
    }


}
