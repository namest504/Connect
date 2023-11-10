package xyz.connect.post.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import xyz.connect.post.web.model.request.CreatePost;
import xyz.connect.post.web.model.request.UpdatePost;
import xyz.connect.post.web.model.response.Post;
import xyz.connect.post.web.service.PostService;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final AccountInfoUtil accountInfoUtil;

    @PostMapping("")
    public ResponseEntity<Post> createPost(@Valid @RequestBody CreatePost createPost,
            HttpServletRequest request) {
        return ResponseEntity.ok(
                postService.createPost(createPost, accountInfoUtil.getAccountId(request)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @GetMapping("")
    public ResponseEntity<List<Post>> getPosts(
            @PageableDefault(sort = "postId", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(postService.getPosts(pageable));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId,
            @Valid @RequestBody UpdatePost updatePost, HttpServletRequest request) {
        return ResponseEntity.ok(
                postService.updatePost(postId, updatePost, accountInfoUtil.getAccountId(request)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, HttpServletRequest request) {
        postService.deletePost(postId, accountInfoUtil.getAccountId(request));
        return ResponseEntity.noContent().build();
    }
}
