package xyz.connect.post.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.connect.post.custom_exception.PostApiException;
import xyz.connect.post.enumeration.ErrorCode;
import xyz.connect.post.util.S3Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UploadImageService {

    private final S3Util s3Util;
    private final Set<String> validMediaType = new HashSet<>(List.of(
            "image/gif", "image/jfif", "image/pjpeg", "image/jpeg", "image/pjp",
            "image/jpg", "image/png", "image/bmp", "image/webp", "image/svgz", "image/svg",
            "image/webp")
    );

    public String uploadImage(MultipartFile multipartFile) {
        if (!isValidImageType(multipartFile)) {
            throw new PostApiException(ErrorCode.INVALID_MEDIA_TYPE);
        }

        try {
            return s3Util.uploadFile(multipartFile);
        } catch (IOException e) {
            throw new PostApiException(ErrorCode.THIRD_PARTY_API_EXCEPTION);
        }
    }

    // 한 개라도 업로드를 허용하지 않는 MediaType 을 가지면 Exception
    public List<String> uploadImages(List<MultipartFile> multipartFiles) {
        List<String> fileNames = new ArrayList<>();
        multipartFiles.forEach(file -> fileNames.add(uploadImage(file)));
        return fileNames;
    }

    private boolean isValidImageType(MultipartFile multipartFile) {
        return validMediaType.contains(multipartFile.getContentType());
    }
}
