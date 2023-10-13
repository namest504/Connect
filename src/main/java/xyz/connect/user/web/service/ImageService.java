package xyz.connect.user.web.service;


import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.connect.user.exception.ErrorCode;
import xyz.connect.user.exception.UserApiException;
import xyz.connect.user.util.S3Util;

@Service
@RequiredArgsConstructor
public class ImageService {


    private final S3Util s3Util;
    private final Set<String> validMediaType = new HashSet<>(List.of(
            "image/gif", "image/jfif", "image/pjpeg", "image/jpeg", "image/pjp",
            "image/jpg", "image/png", "image/bmp", "image/webp", "image/svgz", "image/svg",
            "image/webp")
    );

    public String uploadImage(MultipartFile multipartFile) {
        if (!isValidImageType(multipartFile)) {
            throw new UserApiException(ErrorCode.NOT_SUPPORTED_MEDIA_TYPE);
        }
        try {
            return s3Util.uploadFile(multipartFile);
        } catch (IOException e) {
            throw new UserApiException(ErrorCode.THIRD_PARTY_API_EXCEPTION);
        }
    }

    private boolean isValidImageType(MultipartFile multipartFile) {
        return validMediaType.contains(multipartFile.getContentType());
    }
}
