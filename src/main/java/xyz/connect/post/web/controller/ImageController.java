package xyz.connect.post.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.connect.post.web.model.response.UploadedImage;
import xyz.connect.post.web.service.UploadImageService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ImageController {

    private final UploadImageService uploadImageService;

    @PostMapping("/image")
    public ResponseEntity<UploadedImage> uploadImage(MultipartFile image) {
        return ResponseEntity.ok(uploadImageService.uploadImage(image));
    }

    @PostMapping("/images")
    public ResponseEntity<List<UploadedImage>> uploadImages(List<MultipartFile> image) {
        return ResponseEntity.ok(uploadImageService.uploadImages(image));
    }

}
