package kunuz.uz.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "article")
@Getter
@Setter
public class ArticleEntity {
    /// 6. Article
    ///     id(uuid),title,description,content,shared_count,image_id,
    ///     region_id,category_id,moderator_id,publisher_id,status(Published,NotPublished)
    ///     created_date,published_date,visible,view_count
    ///     (Bitta article bir-nechta type da bo'lishi mumkun. Masalan Asosiy,Muharrir da.)

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "shared_count")
    private Long sharedCount;

    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "region_id")
    private Integer regionId;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "moderator_id")
    private Long moderatorId;

    @Column(name = "publisher_id")
    private Long publisherId;

    @CurrentTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "count")
    private Long count;

    @Column(name = "section_id")
    private Integer sectionId;

    @JoinColumn(name = "section_id", insertable = false,updatable = false,nullable = false)
    @ManyToOne
    private SectionEntity section;

}
