package xyz.connect.post.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.connect.post.custom_exception.PostApiException;
import xyz.connect.post.enumeration.ErrorCode;
import xyz.connect.post.util.S3Util;
import xyz.connect.post.web.model.request.UploadImages;
import xyz.connect.post.web.model.response.UploadedImage;

@Service
@RequiredArgsConstructor
public class UploadImageService {

    private final S3Util s3Util;

    // 한 개라도 업로드를 허용하지 않는 MediaType 을 가지면 Exception
    public List<UploadedImage> uploadImages(UploadImages uploadImages) {
        List<UploadedImage> uploadedImageList = new ArrayList<>();
        uploadImages.images().forEach(file -> uploadedImageList.add(upload(file)));
        return uploadedImageList;
    }

    private UploadedImage upload(MultipartFile multipartFile) {
        try {
            String fileName = s3Util.uploadFile(multipartFile);
            String url = s3Util.getImageUrl(fileName);
            return new UploadedImage(fileName, url);

        } catch (IOException e) {
            throw new PostApiException(ErrorCode.THIRD_PARTY_API_EXCEPTION);
        }
    }
}
