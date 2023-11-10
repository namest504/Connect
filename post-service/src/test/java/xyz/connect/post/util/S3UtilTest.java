package xyz.connect.post.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

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
    void uploadFiles() throws IOException {
        //given
        String filename = "testFile";
        byte[] content = "file contents.".getBytes();
        MockMultipartFile emptyFile = new MockMultipartFile(filename, new byte[0]);
        MockMultipartFile file = new MockMultipartFile(filename, content);
        when(amazonS3.putObject(any())).thenReturn(new PutObjectResult());

        //when
        //then
        assertThat(s3Util.uploadFile(emptyFile)).isNull();
        assertThat(s3Util.uploadFile(null)).isNull();
        assertThat(s3Util.uploadFile(file).length()).isGreaterThan(1);
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