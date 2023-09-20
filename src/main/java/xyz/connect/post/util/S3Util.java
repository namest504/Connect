package xyz.connect.post.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import xyz.connect.post.custom_exception.PostApiException;
import xyz.connect.post.enumeration.ErrorCode;

import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class S3Util {

    private final AmazonS3 amazonS3;
    private final Set<String> validMediaType;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3Util(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
        validMediaType = new HashSet<>(List.of(
                "image/gif", "image/jfif", "image/pjpeg", "image/jpeg", "image/pjp",
                "image/jpg", "image/png", "image/bmp", "image/webp", "image/svgz", "image/svg")
        );
    }

    public void uploadFiles(List<MultipartFile> multipartFiles) throws IOException {
        for (MultipartFile file : multipartFiles) {
            if (!validMediaType.contains(file.getContentType())) {
                throw new PostApiException(ErrorCode.INVALID_MEDIA_TYPE);
            }
        }

        for (MultipartFile multipartFile : multipartFiles) {
            log.info("originalFileName: " + multipartFile.getOriginalFilename());
            log.info("size: " + multipartFile.getSize());
            log.info("contentType: " + multipartFile.getContentType());

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            amazonS3.putObject(bucket, UUID.randomUUID().toString(), multipartFile.getInputStream(), metadata);
        }
    }

    //S3 에 업로드된 파일명을 찾아 Public url 을 리턴
    public List<String> getImageUrl(List<String> pathList) {
        if (pathList == null || pathList.isEmpty()) {
            return new ArrayList<>();
        }

        return pathList.stream()
                .filter(path -> amazonS3.doesObjectExist(bucket, path))
                .map(path -> amazonS3.getUrl(bucket, path).toString())
                .toList();
    }
}
