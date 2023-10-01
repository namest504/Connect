package xyz.connect.post.web.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import xyz.connect.post.web.entity.PostEntity;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void createNewPost() {
        //given
        PostEntity postEntity = generatePostEntity();

        //when
        PostEntity resultEntity = postRepository.save(postEntity);

        //then
        assertThat(postEntity.getPostId()).isEqualTo(resultEntity.getPostId());
        assertThat(postEntity.getAccountId()).isEqualTo(resultEntity.getAccountId());
    }

    private PostEntity generatePostEntity() {
        PostEntity postEntity = new PostEntity();
        postEntity.setPostId(1L);
        postEntity.setAccountId(1L);
        postEntity.setContent("content");
        postEntity.setImages("image");
        return postEntity;
    }
}