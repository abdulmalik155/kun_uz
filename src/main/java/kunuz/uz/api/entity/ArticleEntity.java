package kunuz.uz.api.entity;

import jakarta.persistence.*;
import kunuz.uz.api.enums.ArticleStatus;
import kunuz.uz.api.enums.Language;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "article")
@Getter
@Setter
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false, columnDefinition = "text")
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "language", columnDefinition = "varchar default 'UZ'")
    @Enumerated(value = EnumType.STRING)
    private Language language = Language.UZ;

    @Column(name = "shared_count")
    private Long sharedCount = 0L;

    @Column(name = "image_id")
    private String imageId;

    @ManyToOne(fetch = FetchType.LAZY) // buyerda OneToOne ham bo'lishi mumkun.
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private AttachEntity image;

    @Column(name = "region_id")
    private Integer regionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false, nullable = false)
    private RegionEntity region;

    @Column(name = "moderator_id")
    private Long moderatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", insertable = false, updatable = false, nullable = false)
    private ProfileEntity moderator;

    @Column(name = "publisher_id")
    private Long publisherId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
    private ProfileEntity publisher;

    @Column(name = "status", nullable = false, columnDefinition = "varchar default 'NOT_PUBLISHED'")
    @Enumerated(EnumType.STRING)
    private ArticleStatus status = ArticleStatus.NOT_PUBLISHED;

    @Column(name = "read_time")
    private Integer readTime = 5;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by", insertable = false, updatable = false)
    private ProfileEntity deletedByProfile;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    private ProfileEntity updatedByProfile;

    @Column(name = "visible", columnDefinition = "boolean default true", nullable = false)
    private Boolean visible = true;

    @Column(name = "view_count", columnDefinition = "bigInt default 0")
    private Long viewCount = 0L;

    @Column(name = "like_count", columnDefinition = "bigInt default 0")
    private Long likeCount = 0L;

    @OneToMany(mappedBy = "article")
    private List<ArticleCategoryEntity> categoryList;

    @OneToMany(mappedBy = "article")
    private List<ArticleSectionEntity> sectionList;

    @OneToMany(mappedBy = "article")
    private List<ArticleTagEntity> tagList;

}
