package xyz.connect.user.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "카카오 콜백 응답")

public record KakaoCallbackResponse(
        @JsonProperty
        String code

) {

}