package xyz.connect.user.web.service;

import static org.mockito.Mockito.when;

import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.connect.user.util.S3Util;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
})
public class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @MockBean
    private S3Util s3Util;

    @Mock
    private MultipartFile multipartFile;

    @Test
    void uploadImage_로이미지를업로드할수있다() throws IOException {
        //given
        when(multipartFile.getContentType()).thenReturn("image/jpeg");
        when(s3Util.uploadFile(multipartFile)).thenReturn("image123.jpg");
        when(s3Util.getImageUrl("image123.jpg")).thenReturn("https://example.com/image123.jpg");
        //when
        String imageUrl = imageService.uploadImage(multipartFile);

        //then
        Assertions.assertThat(imageUrl).isNotEmpty();

    }
}