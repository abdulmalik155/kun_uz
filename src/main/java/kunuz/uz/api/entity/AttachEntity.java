package kunuz.uz.api.entity;

import jakarta.persistence.*;
import kunuz.uz.api.dto.response.AttachResponseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "attach")
@Getter
@Setter
public class AttachEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "original_name", nullable = false, columnDefinition = "text")
    private String originalName;

    @Column(name = "path", nullable = false, columnDefinition = "text")
    private String path;

    @Column(name = "size", nullable = false)
    private Integer size;

    @Column(name = "extension", nullable = false)
    private String extension;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public AttachResponseDTO toResponseDTO(){
        AttachResponseDTO dto = new AttachResponseDTO();
        dto.setId(this.getId());
        dto.setPath(this.getPath());
        return dto;
    }
}
