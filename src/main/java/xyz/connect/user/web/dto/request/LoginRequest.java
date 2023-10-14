package xyz.connect.user.web.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public record LoginRequest(
        @NotEmpty
        @JsonProperty
        @Size(max = 512, message = "512자를 넘을 수 없습니다.")
        String email,

        @NotEmpty
        @JsonProperty
        @Size(max = 32, message = "32자를 넘을 수 없습니다.")
        String password

) {

}