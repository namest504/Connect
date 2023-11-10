package xyz.connect.post.web.repository.redis;

import org.springframework.data.repository.CrudRepository;
import xyz.connect.post.web.entity.redis.PostViewsEntity;

public interface PostViewsRedisRepository extends CrudRepository<PostViewsEntity, Long> {

}
