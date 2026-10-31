package kunuz.uz.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class CommentEntity {

    //11. Comment
    //    id,created_date,update_date,profile_id,content,article_id,reply_id,visible.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "profile_id")
    private Long profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false, nullable = false)
    private ProfileEntity profile;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "article_id")
    private UUID articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false, nullable = false)
    private ArticleEntity article;

    @Column(name = "reply_id")
    private Long replyId;

    @Column(name = "visible", columnDefinition = "boolean default true", nullable = false)
    private Boolean visible = true;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;



}
