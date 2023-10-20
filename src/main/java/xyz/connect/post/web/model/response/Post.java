package xyz.connect.post.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {

    private Long postId;
    private Long accountId;
    private String content;
    private List<String> images;
    private Date createdAt;
}
