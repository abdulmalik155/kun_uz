package kunuz.uz.api.entity;

import jakarta.persistence.*;
import kunuz.uz.api.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profile_role")
@Getter
@Setter
public class ProfileRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ProfileRole role;

    @Column(name = "profile_id")
    private Long profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false, nullable = false)
    private ProfileEntity profile;
}
