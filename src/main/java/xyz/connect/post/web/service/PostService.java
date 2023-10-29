package xyz.connect.post.web.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.connect.post.custom_exception.PostApiException;
import xyz.connect.post.enumeration.ErrorCode;
import xyz.connect.post.event.DeletedPostEvent;
import xyz.connect.post.event.UpdatedPostEvent;
import xyz.connect.post.web.entity.PostEntity;
import xyz.connect.post.web.entity.redis.PostViewsEntity;
import xyz.connect.post.web.model.request.CreatePost;
import xyz.connect.post.web.model.request.UpdatePost;
import xyz.connect.post.web.model.response.Post;
import xyz.connect.post.web.model.response.PostDetail;
import xyz.connect.post.web.repository.PostRepository;
import xyz.connect.post.web.repository.redis.PostViewsRedisRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final PostViewsRedisRepository postViewRedisRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Post createPost(CreatePost createPost, long accountId) {
        PostEntity postEntity = modelMapper.map(createPost, PostEntity.class);
        postEntity.setAccountId(accountId);
        postEntity.setContent(createPost.content());

        PostEntity resultEntity = postRepository.save(postEntity);
        Post post = modelMapper.map(resultEntity, Post.class);
        log.info("Post 등록 완료: " + postEntity);

        return post;
    }

    // 댓글 리스트가 포함된 PostDetail 을 반환
    public PostDetail getPost(Long postId) {
        PostEntity postEntity = findPost(postId);
        Post post = modelMapper.map(postEntity, Post.class);

        // getCachedViews() 와 increaseCachedViews() 실행 간격 사이에 스케쥴러가 실행되어
        // 캐싱된 조회수가 사라질 가능성이 존재한다. 이 경우 조회수 증가는 무시된다.
        // 하지만 매우 적은 확률이고, Post 조회수는 오차가 발생하더라도 큰 문제가 없다.
        // 따라서 검증과정을 거치지 않는 것이 효율적이라 판단
        long cachedViews = getCachedViews(postEntity);
        post.setViews(cachedViews);
        increaseCachedViews(postEntity);
        log.info("Post 조회 완료: " + post);
        return post;
    }

    // 댓글이 포함되지 않은 Post 를 반환
    public List<Post> getPosts(Pageable pageable) {
        List<PostEntity> postEntityList = postRepository.findAll(pageable).getContent();
        List<Post> posts = new ArrayList<>();
        for (var postEntity : postEntityList) {
            Post post = modelMapper.map(postEntity, Post.class);
            long cachedViews = getCachedViews(postEntity);
            post.setViews(cachedViews);
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
            if (postEntity.getImages() != null) { // 이미지가 존재했었다면 삭제 이벤트 실행
                List<String> originalImages = Arrays.stream(postEntity.getImages().split(";"))
                        .filter(Objects::nonNull)
                        .toList();
                eventPublisher.publishEvent(
                        new UpdatedPostEvent(originalImages, updatePost.images()));
            }
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

        List<String> images = Arrays.stream(postEntity.getImages().split(";"))
                .filter(Objects::nonNull)
                .toList();
        eventPublisher.publishEvent(new DeletedPostEvent(images));

        postRepository.delete(postEntity);
        log.info(postEntity.getPostId() + "번 Post 삭제 완료");
    }

    public PostEntity findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostApiException(ErrorCode.NOT_FOUND));
    }

    // 1. 캐싱된 조회수를 가져온다.
    //   1) 캐싱된 조회수가 존재하면 리턴한다.
    //   2) 캐싱된 조회수가 없으면 캐싱 후 전달받은 PostEntity 의 조회수를 리턴한다.
    private long getCachedViews(PostEntity postEntity) {
        PostViewsEntity postViewsEntity = getPostViewsEntityOrNew(postEntity.getPostId());

        long cachedViews = postViewsEntity.getViews();
        if (cachedViews < 1) {
            cachedViews = postEntity.getViews();
        }

        postViewsEntity.setViews(cachedViews);
        postViewRedisRepository.save(postViewsEntity);

        return cachedViews;
    }

    // 캐싱된 조회수를 1 증가시킨다
    private void increaseCachedViews(PostEntity postEntity) {
        PostViewsEntity postViewsEntity = getPostViewsEntityOrNew(postEntity.getPostId());
        postViewsEntity.setViews(postViewsEntity.getViews() + 1);
        postViewRedisRepository.save(postViewsEntity);
    }

    // new PostViewsEntity 는 views == 0
    private PostViewsEntity getPostViewsEntityOrNew(long postId) {
        return postViewRedisRepository.findById(postId).orElse(new PostViewsEntity(postId));
    }
}
