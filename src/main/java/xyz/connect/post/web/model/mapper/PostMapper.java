package xyz.connect.post.web.model.mapper;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import xyz.connect.post.util.S3Util;
import xyz.connect.post.web.entity.PostEntity;
import xyz.connect.post.web.model.response.Post;

@Component
@RequiredArgsConstructor
public class PostMapper extends PropertyMap<PostEntity, Post> {

    private final S3Util s3Util;

    @Override
    protected void configure() {
        map().setPostId(source.getPostId());
        map().setAccountId(source.getAccountId());
        map().setContent(source.getContent());
        map().setImages(imageStringToList(source.getImages()));
    }

    private List<String> imageStringToList(String images) {
        List<String> imageList = new ArrayList<>();
        for (String image : images.split(";")) {
            imageList.add(s3Util.getImageUrl(image));
        }

        return imageList;
    }
}
