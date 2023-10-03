package xyz.connect.post.web.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.connect.post.web.entity.PostEntity;

@Repository
public interface PostRepository extends CrudRepository<PostEntity, Long> {
}
