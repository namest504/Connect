package xyz.connect.post.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.connect.post.web.service.UploadImageService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final UploadImageService uploadImageService;

    // TODO: postId 에 담긴 이미지 Url 리턴하는 엔드포인트

    @PostMapping("/")
    public ResponseEntity<String> uploadImage(MultipartFile image) {
        return ResponseEntity.ok(uploadImageService.uploadImage(image));
    }

    @PostMapping("/list")
    public ResponseEntity<List<String>> uploadImages(List<MultipartFile> image) {
        return ResponseEntity.ok(uploadImageService.uploadImages(image));
    }

}
