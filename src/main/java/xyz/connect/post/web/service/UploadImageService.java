package xyz.connect.post.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.connect.post.custom_exception.PostApiException;
import xyz.connect.post.enumeration.ErrorCode;
import xyz.connect.post.util.S3Util;
import xyz.connect.post.web.model.response.UploadedImage;

@Service
@RequiredArgsConstructor
public class UploadImageService {

    private final S3Util s3Util;
    private final Set<String> validMediaType = new HashSet<>(List.of(
            "image/gif", "image/jfif", "image/pjpeg", "image/jpeg", "image/pjp",
            "image/jpg", "image/png", "image/bmp", "image/webp", "image/svgz", "image/svg",
            "image/webp")
    );

    public UploadedImage uploadImage(MultipartFile multipartFile) {
        if (!isValidImageType(multipartFile)) {
            throw new PostApiException(ErrorCode.INVALID_MEDIA_TYPE);
        }

        try {
            String fileName = s3Util.uploadFile(multipartFile);
            String url = s3Util.getImageUrl(fileName);
            return new UploadedImage(fileName, url);

        } catch (IOException e) {
            throw new PostApiException(ErrorCode.THIRD_PARTY_API_EXCEPTION);
        }
    }

    // 한 개라도 업로드를 허용하지 않는 MediaType 을 가지면 Exception
    public List<UploadedImage> uploadImages(List<MultipartFile> multipartFiles) {
        List<UploadedImage> uploadedImageList = new ArrayList<>();
        multipartFiles.forEach(file -> uploadedImageList.add(uploadImage(file)));
        return uploadedImageList;
    }

    private boolean isValidImageType(MultipartFile multipartFile) {
        return validMediaType.contains(multipartFile.getContentType());
    }
}
