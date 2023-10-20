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
import xyz.connect.post.util.S3Util;
import xyz.connect.post.web.entity.PostEntity;
import xyz.connect.post.web.entity.redis.PostViewsEntity;
import xyz.connect.post.web.model.request.CreatePost;
import xyz.connect.post.web.model.request.UpdatePost;
import xyz.connect.post.web.model.response.Post;
import xyz.connect.post.web.repository.PostRepository;
import xyz.connect.post.web.repository.redis.PostViewsRedisRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final PostViewsRedisRepository postViewRedisRepository;
    private final S3Util s3Util;

    public Post createPost(CreatePost createPost, long accountId) {
        PostEntity postEntity = modelMapper.map(createPost, PostEntity.class);
        postEntity.setAccountId(accountId);
        postEntity.setContent(createPost.content());

        PostEntity resultEntity = postRepository.save(postEntity);
        Post post = modelMapper.map(resultEntity, Post.class);
        log.info("Post 등록 완료: " + postEntity);

        return post;
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
            Post post = modelMapper.map(entity, Post.class);
            posts.add(post);
        }

        log.info("Post " + posts.size() + "개 조회 완료");
        return posts;
    }

    public Post updatePost(Long postId, UpdatePost updatePost, long accountId) {
        PostEntity postEntity = findPost(postId);
        if (postEntity.getAccountId() != accountId) {
            throw new PostApiException(ErrorCode.UNAUTHORIZED);
        }

        postEntity.setContent(updatePost.content());
        if (updatePost.images() != null && !updatePost.images().isEmpty()) {
            postEntity.setImages(String.join(";", updatePost.images()));
        }

        PostEntity resultEntity = postRepository.save(postEntity);
        Post post = modelMapper.map(resultEntity, Post.class);
        log.info("Post 수정 완료: " + post);
        return post;
    }

    public void deletePost(Long postId, long accountId) {
        PostEntity postEntity = findPost(postId);
        if (postEntity.getAccountId() != accountId) {
            throw new PostApiException(ErrorCode.UNAUTHORIZED);
        }

        String[] images = postEntity.getImages().split(";");
        for (String image : images) {
            s3Util.deleteFile(image); // TODO: 2023-10-18 이벤트 방식으로 변경
        }

        postRepository.delete(postEntity);
        log.info(postEntity.getPostId() + "번 Post 삭제 완료");
    }

    public void increaseViews(Long postId) {
        findPost(postId); //postId 유효성 검증
        PostViewsEntity postViewsEntity = postViewRedisRepository.findById(postId).orElse(
                new PostViewsEntity(postId)
        );

        postViewsEntity.setViews(postViewsEntity.getViews() + 1);
        postViewRedisRepository.save(postViewsEntity);
    }

    public PostEntity findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostApiException(ErrorCode.NOT_FOUND));
    }
}
