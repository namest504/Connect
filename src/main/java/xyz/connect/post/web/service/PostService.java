package xyz.connect.post.web.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.connect.post.custom_exception.PostApiException;
import xyz.connect.post.enumeration.ErrorCode;
import xyz.connect.post.web.entity.PostEntity;
import xyz.connect.post.web.entity.redis.PostViewsEntity;
import xyz.connect.post.web.model.request.CreatePost;
import xyz.connect.post.web.model.request.UpdatePost;
import xyz.connect.post.web.model.response.Post;
import xyz.connect.post.web.repository.PostRepository;
import xyz.connect.post.web.repository.redis.PostViewsRedisRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final PostViewsRedisRepository postViewRedisRepository;

    public void createPost(CreatePost createPost) {
        // TODO: UserService API 로 accountId 검증
        PostEntity postEntity = new PostEntity();
        postEntity.setAccountId(1L); // TODO: 추후 삭제
        postEntity.setContent(createPost.content());
        postEntity.setImages(joinToStringAtSemicolon(createPost.imageUrl()));

        postRepository.save(postEntity);
        log.info("Post 업로드 완료: " + postEntity);
    }

    public Post getPost(Long postId) {
        PostEntity postEntity = findPost(postId);
        Post post = modelMapper.map(postEntity, Post.class);
        log.info("Post 조회 완료: " + post);
        return post;
    }

    public List<Post> getPosts(Pageable pageable) {
        List<PostEntity> postEntityList = postRepository.findAll(pageable).getContent();
        List<Post> posts = new ArrayList<>();
        for (var entity : postEntityList) {
            posts.add(modelMapper.map(entity, Post.class));
        }

        log.info("Post " + posts.size() + "개 조회 완료");
        return posts;
    }

    public void updatePost(Long postId, UpdatePost updatePost) {
        PostEntity postEntity = findPost(postId);
        postEntity.setContent(updatePost.content());
        postEntity.setImages(joinToStringAtSemicolon(updatePost.imageUrl()));

        postRepository.save(postEntity);
    }

    public void deletePost(Long postId) {
        PostEntity postEntity = findPost(postId);
        postRepository.delete(postEntity);
    }

    public void increaseViews(Long postId) {
        findPost(postId); //postId 유효성 검증
        PostViewsEntity postViewsEntity = postViewRedisRepository.findById(postId).orElse(
                new PostViewsEntity(postId)
        );

        postViewsEntity.setViews(postViewsEntity.getViews() + 1);
        postViewRedisRepository.save(postViewsEntity);
    }

    private PostEntity findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostApiException(ErrorCode.POST_NOT_FOUND));
    }

    private String joinToStringAtSemicolon(List<String> stringList) {
        var sb = new StringBuilder();
        if (stringList != null) {
            for (var s : stringList) {
                sb.append(s).append(";");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
