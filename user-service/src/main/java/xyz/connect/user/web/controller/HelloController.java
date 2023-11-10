package xyz.connect.user.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.connect.user.web.dto.response.HelloResponse;

@Tag(name = "HelloController", description = "테스트 용도")
@RestController
public class HelloController {
    
    @Operation(summary = "태스트용 api ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "테스트 성공")}
    )
    @GetMapping("/hello")
    public ResponseEntity<HelloResponse> hello() {
        HelloResponse helloResponse = new HelloResponse("Hello");
        return ResponseEntity.ok(helloResponse);
    }
}
