package xyz.connect.post.web.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import xyz.connect.post.custom_exception.PostApiException;
import xyz.connect.post.enumeration.ErrorCode;
import xyz.connect.post.web.entity.CommentEntity;
import xyz.connect.post.web.entity.PostEntity;
import xyz.connect.post.web.model.request.CreateComment;
import xyz.connect.post.web.model.request.UpdateComment;
import xyz.connect.post.web.model.response.Comment;
import xyz.connect.post.web.repository.CommentRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final ModelMapper modelMapper;

    public List<Comment> getComments(Long postId) {
        PostEntity postEntity = postService.findPost(postId);
        List<Comment> commentList = new ArrayList<>();
        for (CommentEntity entity : postEntity.getComments()) {
            commentList.add(modelMapper.map(entity, Comment.class));
        }

        log.info(postEntity.getPostId() + "번 Post의 Comment " + commentList.size() + "개 조회 완료");
        return commentList;
    }

    public Comment createComment(CreateComment createComment, long accountId) {
        PostEntity postEntity = postService.findPost(createComment.postId());

        CommentEntity commentEntity = modelMapper.map(createComment, CommentEntity.class);
        commentEntity.setPost(postEntity);
        commentEntity.setAccountId(accountId);
        commentEntity.setContent(createComment.content());
        CommentEntity resultEntity = commentRepository.save(commentEntity);

        Comment comment = modelMapper.map(resultEntity, Comment.class);
        log.info("Comment 등록 완료: " + comment);
        return comment;
    }

    public Comment updateComment(Long commentId, UpdateComment updateComment, long accountId) {
        CommentEntity commentEntity = findComment(commentId);
        if (commentEntity.getAccountId() != accountId) {
            throw new PostApiException(ErrorCode.UNAUTHORIZED);
        }

        commentEntity.setContent(updateComment.content());
        CommentEntity resultEntity = commentRepository.save(commentEntity);
        Comment comment = modelMapper.map(resultEntity, Comment.class);
        log.info("Comment 수정 완료: " + comment);
        return comment;
    }

    public void deleteComment(Long commentId, long accountId) {
        CommentEntity commentEntity = findComment(commentId);
        if (commentEntity.getAccountId() != accountId) {
            throw new PostApiException(ErrorCode.UNAUTHORIZED);
        }

        commentRepository.delete(commentEntity);
        log.info(commentEntity.getPost() + "번 Post 의 " + commentEntity.getCommentId()
                + "번 Comment 삭제 완료");
    }

    public CommentEntity findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new PostApiException(ErrorCode.NOT_FOUND));
    }
}
