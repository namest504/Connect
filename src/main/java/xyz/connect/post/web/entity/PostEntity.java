package xyz.connect.post.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Entity(name = "POST")
@Data
public class PostEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(columnDefinition = "BIGINT", nullable = false)
    private Long accountId;

    @Column(columnDefinition = "VARCHAR(512)", nullable = false)
    private String content;

    @Column(columnDefinition = "text")
    private String images; // 공백없이 ; 를 구분자로 하는 url 목록

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    List<CommentEntity> comments = new ArrayList<>();
}
