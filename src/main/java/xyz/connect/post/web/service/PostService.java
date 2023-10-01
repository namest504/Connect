package xyz.connect.post.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.connect.post.web.entity.PostEntity;
import xyz.connect.post.web.model.request.CreatePost;
import xyz.connect.post.web.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    public void createPost(CreatePost createPost) {
        // TODO: UserService API 로 accountId 검증
        StringBuilder sb = new StringBuilder();
        PostEntity postEntity = new PostEntity();
        postEntity.setAccountId(1L); // TODO: 추후 삭제
        postEntity.setContent(createPost.content());

        if (createPost.imageUrl() != null) {
            for (String url : createPost.imageUrl()) {
                sb.append(url).append(";");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        postEntity.setImages(sb.toString());

        postRepository.save(postEntity);
        log.info("Post 업로드 완료: " + postEntity);
    }
}
