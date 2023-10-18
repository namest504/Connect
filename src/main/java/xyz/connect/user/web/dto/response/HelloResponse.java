package xyz.connect.user.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HelloResponse(
        @JsonProperty
        String hello

) {

}