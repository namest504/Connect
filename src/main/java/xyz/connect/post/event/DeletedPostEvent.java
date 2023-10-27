package xyz.connect.post.event;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeletedPostEvent {

    private List<String> images;
}
