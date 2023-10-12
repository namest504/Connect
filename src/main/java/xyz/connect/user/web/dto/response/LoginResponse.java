package xyz.connect.user.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponse(

        @JsonProperty
        String AccessToken,

        @JsonProperty
        String RefreshToken

) {

}