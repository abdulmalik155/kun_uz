package kunuz.uz.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "article_type")
@Getter
@Setter
public class ArticleTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_type_seq_gen")
    @SequenceGenerator(name = "article_type_seq_gen",
            sequenceName = "article_type_seq_db",
            allocationSize = 50
    )
    private Integer id;

    @Column(name = "order_number", nullable = false, unique = true)
    private Integer orderNumber;

    @Column(name = "name_uz", nullable = false, unique = true, columnDefinition = "varchar(255) default 'placeholder uzbek'")
    private String nameUz;

    @Column(name = "name_ru", nullable = false, unique = true, columnDefinition = "varchar(255) default 'placeholder russian'")
    private String nameRu;

    @Column(name = "name_en", nullable = false, unique = true, columnDefinition = "varchar(255) default 'placeholder english'")
    private String nameEn;

    @Column(name = "visible", nullable = false, columnDefinition = "boolean default true")
    private Boolean visible = true;

    @CurrentTimestamp
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
}
