package xyz.connect.post.event;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.connect.post.util.S3Util;

@Component
@Slf4j
@RequiredArgsConstructor
public class PostEventHandler {

    private final S3Util s3Util;

    @EventListener
    public void deleteImageFromS3(DeletedPostEvent deletedPostEvent) {
        deleteImages(deletedPostEvent.getImages());
    }

    @EventListener
    public void deleteImageFromS3(UpdatedPostEvent updatedPostEvent) {
        Set<String> updatedImages = new HashSet<>(updatedPostEvent.getUpdatedImages());
        List<String> target = updatedPostEvent.getOriginalImages().stream()
                .filter(o -> !updatedImages.contains(o))
                .toList();

        deleteImages(target);
    }

    private void deleteImages(List<String> images) {
        images.forEach(s3Util::deleteFile);
        log.info("[Event] " + images.size() + "개 이미지 삭제 완료");
    }
}
