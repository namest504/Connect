package xyz.connect.post.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.connect.post.web.model.request.CreatePost;
import xyz.connect.post.web.model.request.UpdatePost;
import xyz.connect.post.web.model.response.Post;
import xyz.connect.post.web.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/")
    public ResponseEntity<Void> createPost(@RequestBody CreatePost createPost) {
        postService.createPost(createPost);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @GetMapping("/")
    public ResponseEntity<List<Post>> getPosts(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(postService.getPosts(pageable));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, UpdatePost updatePost) {
        postService.updatePost(postId, updatePost);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/views/{postId}")
    public ResponseEntity<Void> increaseViews(@PathVariable Long postId) {
        postService.increaseViews(postId);
        return ResponseEntity.noContent().build();
    }
}
