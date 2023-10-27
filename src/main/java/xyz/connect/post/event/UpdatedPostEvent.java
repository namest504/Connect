package xyz.connect.post.event;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatedPostEvent {

    private List<String> originalImages;
    private List<String> updatedImages;
}
