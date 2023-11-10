package xyz.connect.post.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.connect.post.util.AccountInfoUtil;
import xyz.connect.post.web.model.request.CreateComment;
import xyz.connect.post.web.model.request.UpdateComment;
import xyz.connect.post.web.model.response.Comment;
import xyz.connect.post.web.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final AccountInfoUtil accountInfoUtil;

    @GetMapping("")
    public ResponseEntity<List<Comment>> getComment(Long postId,
            @PageableDefault(sort = "commentId", direction = Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(commentService.getComments(postId, pageable));
    }

    @PostMapping("")
    public ResponseEntity<Comment> createComment(@RequestBody CreateComment createComment,
            HttpServletRequest request) {
        return ResponseEntity.ok(
                commentService.createComment(createComment, accountInfoUtil.getAccountId(request)));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId,
            @RequestBody UpdateComment updateComment, HttpServletRequest request) {
        return ResponseEntity.ok(commentService.updateComment(commentId, updateComment,
                accountInfoUtil.getAccountId(request)));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
            HttpServletRequest request) {
        commentService.deleteComment(commentId, accountInfoUtil.getAccountId(request));
        return ResponseEntity.noContent().build();
    }
}
