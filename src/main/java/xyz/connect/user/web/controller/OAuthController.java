package xyz.connect.user.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.connect.user.web.dto.response.LoginResponse;
import xyz.connect.user.web.service.OAuth.OAuthService;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<String> kakaoCallBack(@RequestParam String code) {
        return ResponseEntity.ok(code);
    }

    @PostMapping("/loginKakao")
    public ResponseEntity<LoginResponse> loginKakao(@RequestParam String code) {

        LoginResponse loginResponse = oAuthService.loginKakao(code);
        return ResponseEntity.ok(loginResponse);
    }
}
