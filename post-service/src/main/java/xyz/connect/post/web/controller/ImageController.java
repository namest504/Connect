package xyz.connect.post.web.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.connect.post.web.model.request.UploadImages;
import xyz.connect.post.web.model.response.UploadedImage;
import xyz.connect.post.web.service.UploadImageService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ImageController {

    private final UploadImageService uploadImageService;

    @PostMapping("/images")
    public ResponseEntity<List<UploadedImage>> uploadImages(@Valid UploadImages uploadImages) {
        return ResponseEntity.ok(uploadImageService.uploadImages(uploadImages));
    }

}
