package kunuz.uz.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "category")
@Getter
@Setter

public class CategoryEntity {
//4. Category(O'zbekiston, Jahon, Iksodiyot, Sport, Audio, Video, Jamiyat)
//    id,order_number,nameUz, nameRu, nameEn, key, visible,created_date
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Boolean visible;

    @Column(name = "key", unique = true, nullable = false, columnDefinition = "varchar(255) default 'Category'")
    private String key;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
}
