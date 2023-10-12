package xyz.connect.user.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.connect.user.web.dto.request.CreateUserRequest;
import xyz.connect.user.web.dto.request.LoginRequest;
import xyz.connect.user.web.dto.response.LoginResponse;
import xyz.connect.user.web.service.UserService;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final UserService userService;


    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody CreateUserRequest createUserRequest) {
        userService.createUser(createUserRequest);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
}
