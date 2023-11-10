package xyz.connect.user.web.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이미지 업로드 응답")
public record UploadImageResponse(
        @JsonProperty
        String imageUrl

) {

}