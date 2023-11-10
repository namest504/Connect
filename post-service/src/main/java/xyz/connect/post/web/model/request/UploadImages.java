package xyz.connect.post.web.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import xyz.connect.post.validator.CheckContentType;

public record UploadImages(
        @NotEmpty(message = "필수항목입니다.")
        @Size(max = 10, message = "한 번에 최대 10개 까지만 업로드가 가능합니다.")
        @CheckContentType(message = "지원하지 않는 Content-Type 입니다.")
        List<MultipartFile> images

) {

}
