package xyz.connect.user.web.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


@Schema(description = "로그인 요청")
public record LoginRequest(
        @NotEmpty
        @JsonProperty
        @Schema(description = "email", example = "connect@naver.com")
        @Size(max = 512, message = "512자를 넘을 수 없습니다.")
        String email,

        @NotEmpty
        @JsonProperty
        @Schema(description = "password", example = "141414")
        @Size(max = 32, message = "32자를 넘을 수 없습니다.")
        String password

) {

}