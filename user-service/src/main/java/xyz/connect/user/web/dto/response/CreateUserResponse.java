package xyz.connect.user.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 응답")
public record CreateUserResponse(
        @JsonProperty
        String email

) {

}