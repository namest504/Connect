package xyz.connect.user.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "이메일 중복확인 응답")
public record CheckEmailResponse(
        @JsonProperty
        Boolean is_true

) {

}