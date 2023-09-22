package xyz.connect.post.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import xyz.connect.post.custom_exception.PostApiException;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class S3UtilTest {

    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    S3Util s3Util;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadFiles() {
        //given
        String filename = "testFile";
        byte[] content = "file contents.".getBytes();
        MockMultipartFile wrongFile = new MockMultipartFile("original", filename, "wrongType", content);
        when(amazonS3.putObject(any())).thenReturn(new PutObjectResult());

        //when
        //then
        assertThatThrownBy(() -> s3Util.uploadFile(wrongFile))
                .isInstanceOf(PostApiException.class);
    }

    @Test
    void getImageUrl() throws MalformedURLException {
        //given
        String fileName = "testFile";
        String noExistsFileName = "wrong file";
        URL resultUrl = new URL("http://localhost");
        when(amazonS3.getUrl(any(), any())).thenReturn(resultUrl);
        when(amazonS3.doesObjectExist(any(), eq(fileName))).thenReturn(true);
        when(amazonS3.doesObjectExist(any(), eq(noExistsFileName))).thenReturn(false);

        //when
        String result = s3Util.getImageUrl(fileName);
        String noExistsResult = s3Util.getImageUrl(noExistsFileName);
        String nullParameter = s3Util.getImageUrl(null);

        //then
        assertThat(result).isEqualTo(resultUrl.toString());
        assertThat(noExistsResult).isNull();
        assertThat(nullParameter).isNull();
    }

    @Test
    void deleteFile() {
        String fileName = "Will delete file";
        s3Util.deleteFile(fileName);
        verify(amazonS3).deleteObject(any(), eq(fileName));
    }
}