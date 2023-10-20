package xyz.connect.post.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment {

    private long commentId;
    private long accountId;
    private long postId;
    private String content;
    private Date createdAt;
}
