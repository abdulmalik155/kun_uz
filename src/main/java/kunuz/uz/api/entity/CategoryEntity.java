package kunuz.uz.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "category")
@Getter
@Setter

public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    @Column(name = "name_uz", nullable = false, unique = true)
    private String nameUz;

    @Column(name = "name_ru", nullable = false, unique = true, columnDefinition = "varchar(255) default 'placeholder russian'")
    private String nameRu;

    @Column(name = "name_en", nullable = false, unique = true, columnDefinition = "varchar(255) default 'placeholder english'")
    private String nameEn;

    @Column(name = "visible", nullable = false, columnDefinition = "boolean default true")
    private Boolean visible;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
}
