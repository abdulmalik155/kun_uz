package kunuz.uz.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "section")
@Getter
@Setter
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "section_seq_gen")
    @SequenceGenerator(name = "section_seq_gen",
            sequenceName = "section_seq_db",
            allocationSize = 50
    )
    private Integer id;

    @Column(name = "order_number", nullable = false, unique = true)
    private Integer orderNumber;

    @Column(name = "name_uz", nullable = false, unique = true, columnDefinition = "varchar(255) default 'Bölim'")
    private String nameUz;

    @Column(name = "name_ru", nullable = false, unique = true, columnDefinition = "varchar(255) default 'Отдел'")
    private String nameRu;

    @Column(name = "name_en", nullable = false, unique = true, columnDefinition = "varchar(255) default 'Section'")
    private String nameEn;

    @Column(name = "visible", nullable = false, columnDefinition = "boolean default true")
    private Boolean visible = true;

    @Column(name = "key", unique = true, nullable = false, columnDefinition = "varchar(255) default 'section'")
    private String key;


    @CurrentTimestamp
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
}
