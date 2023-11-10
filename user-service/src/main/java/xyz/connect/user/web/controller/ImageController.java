package xyz.connect.user.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.connect.user.web.dto.response.UploadImageResponse;
import xyz.connect.user.web.service.ImageService;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Tag(name = "ImageController", description = "이미지 업로드")
public class ImageController {

    private final ImageService imageService;


    @Operation(summary = "프로필 이미지 업로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 이미지 업로드 성공"),
            @ApiResponse(responseCode = "415", description = "Media Type 을 확인해주세요", content = @Content),
            @ApiResponse(responseCode = "500", description = "Third party error", content = @Content)}
    )
    @PostMapping("/uploadImage")
    public ResponseEntity<UploadImageResponse> uploadImage(MultipartFile image) {
        String imageUrl = imageService.uploadImage(image);
        UploadImageResponse uploadImageResponse = new UploadImageResponse(imageUrl);
        return ResponseEntity.ok(uploadImageResponse);

    }


}
