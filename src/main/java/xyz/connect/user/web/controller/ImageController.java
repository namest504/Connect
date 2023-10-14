package xyz.connect.user.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.connect.user.web.service.ImageService;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(MultipartFile image) {

        return ResponseEntity.ok(imageService.uploadImage(image));

    }


}
