package xyz.connect.user.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.connect.user.web.dto.request.CreateUserRequest;
import xyz.connect.user.web.dto.request.LoginRequest;
import xyz.connect.user.web.dto.response.CheckEmailResponse;
import xyz.connect.user.web.dto.response.CreateUserResponse;
import xyz.connect.user.web.dto.response.LoginResponse;
import xyz.connect.user.web.service.UserService;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Tag(name = "UserController", description = "회원가입, 로그인, 이메일중복체크, 이메일인증")
public class UserController {


    private final UserService userService;

    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "이메일 이나 비밀번호 사이즈 오류", content = @Content),
            @ApiResponse(responseCode = "409", description = "중복된 이메일 입니다", content = @Content)}
    )
    @PostMapping("/sign-up")
    public ResponseEntity<CreateUserResponse> signUp(
            @RequestBody CreateUserRequest createUserRequest) {
        String email = userService.createUser(createUserRequest);
        CreateUserResponse createUserResponse = new CreateUserResponse(email);
        return ResponseEntity.ok(createUserResponse);
    }


    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "404", description = "해당 이메일 유저가 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "비밀번호가 틀렸습니다.", content = @Content)
    }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.loginUser(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }


    @Operation(summary = "이메일 중복확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공, 해당 email 사용가능하면 true 반환")}
    )
    @GetMapping("/checkEmail")
    public ResponseEntity<CheckEmailResponse> check(
            @Schema(description = "email", example = "connect@naver.com")
            @RequestParam String email) {
        Boolean is_true = userService.checkEmail(email);
        CheckEmailResponse checkEmailResponse = new CheckEmailResponse(is_true);
        return ResponseEntity.ok(checkEmailResponse);
    }


}
