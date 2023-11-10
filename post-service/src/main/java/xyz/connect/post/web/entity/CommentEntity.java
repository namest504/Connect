package xyz.connect.post.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@Entity(name = "COMMENT")
@Data
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(columnDefinition = "BIGINT", nullable = false)
    private Long accountId;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "POST_ID", columnDefinition = "BIGINT", nullable = false)
    @ToString.Exclude
    private PostEntity post;
}
