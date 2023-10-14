package xyz.connect.user.util;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Util {

    private final AmazonS3 amazonS3;


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile 받아 해당 파일을 s3에 업로드
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        String fileName = UUID.randomUUID().toString();

        amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
        log.info("File is uploaded. bucket: " + bucket + ", name: " + fileName);
        return fileName;
    }

    //S3 에 업로드된 파일명을 찾아 Public url 을 리턴
    public String getImageUrl(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }

        if (!amazonS3.doesObjectExist(bucket, fileName)) {
            return null;
        }

        return amazonS3.getUrl(bucket, fileName).toString();
    }


    //S3 업로드된 파일명을 받아 삭제
    public void deleteFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return;
        }
        amazonS3.deleteObject(bucket, fileName);
    }

}
