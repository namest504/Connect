package xyz.connect.post.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDetail {

    private Long postId;
    private Long accountId;
    private String content;
    private List<String> images;
    private Date createdAt;
    private Long views;
    @ToString.Exclude
    private List<Comment> comments;
}
