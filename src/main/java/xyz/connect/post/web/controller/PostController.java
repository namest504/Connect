package xyz.connect.post.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.connect.post.web.model.request.CreatePost;
import xyz.connect.post.web.service.PostService;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/")
    public ResponseEntity<Void> createPost(@RequestBody CreatePost createPost) {
        postService.createPost(createPost);
        return ResponseEntity.noContent().build();
    }
}
