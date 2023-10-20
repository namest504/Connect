package xyz.connect.post.config;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.connect.post.util.S3Util;
import xyz.connect.post.web.entity.CommentEntity;
import xyz.connect.post.web.entity.PostEntity;
import xyz.connect.post.web.model.request.CreatePost;
import xyz.connect.post.web.model.response.Comment;
import xyz.connect.post.web.model.response.Post;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {

    private final ModelMapper modelMapper = new ModelMapper();
    private final S3Util s3Util;

    @Bean
    public ModelMapper modelMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addConverter(postEntityToPost());
        modelMapper.addConverter(createPostToPostEntity());
        modelMapper.addConverter(commentEntityToComment());

        return modelMapper;
    }

    private Converter<PostEntity, Post> postEntityToPost() {
        return new AbstractConverter<>() {
            @Override
            protected Post convert(PostEntity source) {
                Post post = new Post();
                post.setPostId(source.getPostId());
                post.setAccountId(source.getAccountId());
                post.setContent(source.getContent());
                post.setImages(imageStringToList(source.getImages()));
                post.setCreatedAt(source.getCreatedAt());
                return post;
            }
        };
    }

    private Converter<CreatePost, PostEntity> createPostToPostEntity() {
        return new AbstractConverter<>() {
            @Override
            protected PostEntity convert(CreatePost source) {
                PostEntity postEntity = new PostEntity();
                postEntity.setPostId(null);
                postEntity.setAccountId(null);
                postEntity.setContent(source.content());
                if (source.images() != null && !source.images().isEmpty()) {
                    postEntity.setImages(String.join(";", source.images()));
                }
                return postEntity;
            }
        };
    }

    private Converter<CommentEntity, Comment> commentEntityToComment() {
        return new AbstractConverter<>() {
            @Override
            protected Comment convert(CommentEntity source) {
                Comment comment = new Comment();
                comment.setCommentId(source.getCommentId());
                comment.setPostId(source.getPost().getPostId());
                comment.setAccountId(source.getAccountId());
                comment.setContent(source.getContent());
                comment.setCreatedAt(source.getCreatedAt());
                return comment;
            }
        };
    }

    private List<String> imageStringToList(String images) {
        List<String> imageList = new ArrayList<>();
        for (String image : images.split(";")) {
            imageList.add(s3Util.getImageUrl(image));
        }

        return imageList;
    }
}
