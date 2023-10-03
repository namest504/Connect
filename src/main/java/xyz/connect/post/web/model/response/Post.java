package xyz.connect.post.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {
    private Long postId;
    private Long accountId;
    private String content;
    private List<String> images;
}
