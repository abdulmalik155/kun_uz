package kunuz.uz.api.entity;

import jakarta.persistence.*;
import kunuz.uz.api.enums.ProfileRole;
import kunuz.uz.api.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile")
@Getter
@Setter
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq_gen")
    @SequenceGenerator(name = "profile_seq_gen",
            sequenceName = "profile_seq_db",
            allocationSize = 10
    )
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", columnDefinition = "varchar(255) default 'Examplebekov'")
    private String surname = "Examplebekov";

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;

    @Column(name = "role", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ProfileRole role;

    @Column(name = "visible", nullable = false, columnDefinition = "boolean default true")
    private Boolean visible = true;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "photo_id")
    private Long photoId;
}
