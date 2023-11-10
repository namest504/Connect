package xyz.connect.user.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "로그인 응답")
public record LoginResponse(

        @JsonProperty
        String AccessToken,

        @JsonProperty
        String RefreshToken

) {

}