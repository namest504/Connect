package xyz.connect.user.web.service.OAuth;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.connect.user.config.JwtTokenProvider;
import xyz.connect.user.web.dto.params.OAuthInfoParam;
import xyz.connect.user.web.dto.response.LoginResponse;
import xyz.connect.user.web.entity.AccountType;
import xyz.connect.user.web.entity.UserEntity;
import xyz.connect.user.web.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Map<String, RequestInfoHelper> requestInfoHelpers;

    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L; //1시간

    public LoginResponse loginKakao(String code, String requestHelper) {
        OAuthInfoParam oAuthInfoParam = requestInfoHelpers.get(requestHelper).request(code);
        log.info("oAuthInfoParam image ={}", oAuthInfoParam.getImage());
        Long userId = findOrCreateUser(oAuthInfoParam);
        LoginResponse loginResponse = getLoginResponse(userId, oAuthInfoParam);
        return loginResponse;
    }

    private LoginResponse getLoginResponse(Long userId, OAuthInfoParam oAuthInfoParam) {
        String accessToken = jwtTokenProvider.createAccssToken(userId, oAuthInfoParam.getEmail(),
                key, expireTimeMs);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId, oAuthInfoParam.getEmail(),
                key, expireTimeMs);
        LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken);
        return loginResponse;
    }

    private Long findOrCreateUser(OAuthInfoParam oAuthInfoParam) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(oAuthInfoParam.getEmail());
        if (userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();
            return userEntity.getId();
        } else {
            UserEntity newUser = new UserEntity();
            newUser.setEmail(oAuthInfoParam.getEmail());
            newUser.setAccount_type(AccountType.KAKAO);
            newUser.setProfile_image_url(oAuthInfoParam.getImage());
            userRepository.save(newUser);
            return newUser.getId();
        }
    }


}
