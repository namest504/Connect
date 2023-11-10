package xyz.connect.post.web.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateComment(
        @NotEmpty(message = "필수 항목입니다.")
        Long postId,

        @NotEmpty(message = "필수 항목입니다.")
        @Size(max = 128, message = "128자를 넘을 수 없습니다.")
        String content
) {

}
