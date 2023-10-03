package xyz.connect.post.web.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;


public record UpdatePost(
        @NotEmpty
        @Size(max = 512, message = "512자를 넘을 수 없습니다.")
        String content,

        @Size(max = 10, message = "포스트 내 이미지는 최대 10개까지 가능합니다.")
        List<String> imageUrl
) {
}