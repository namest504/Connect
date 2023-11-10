package xyz.connect.post.web.entity.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "postViews", timeToLive = 3600)
@Getter
@Setter
public class PostViewsEntity {

    @Id
    private Long postId;
    private long views;

    public PostViewsEntity(Long postId) {
        this.postId = postId;
        this.views = 0;
    }
}
