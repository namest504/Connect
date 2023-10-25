package xyz.connect.user.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.connect.user.web.dto.response.KakaoCallbackResponse;
import xyz.connect.user.web.dto.response.LoginResponse;
import xyz.connect.user.web.service.OAuth.OAuthService;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Tag(name = "OAuthController", description = "카카오로그인, 구글로그인")
public class OAuthController {

    private final OAuthService oAuthService;

    @Operation(summary = "카카오 콜백")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카카오 코드 요청 성공")}
    )
    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<KakaoCallbackResponse> kakaoCallBack(@RequestParam String code) {
        KakaoCallbackResponse kakaoCallbackResponse = new KakaoCallbackResponse(code);
        return ResponseEntity.ok(kakaoCallbackResponse);
    }


    @Operation(summary = "카카오 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카카오 로그인 성공")}
    )
    @PostMapping("/loginKakao")
    public ResponseEntity<LoginResponse> loginKakao(@RequestParam String code) {
        LoginResponse loginResponse = oAuthService.loginKakao(code, "KakaoRequestInfoHelper");
        return ResponseEntity.ok(loginResponse);
    }
}
