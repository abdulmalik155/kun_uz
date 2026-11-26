package kunuz.uz.api.dto.response;

import kunuz.uz.api.entity.AttachEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ArticleShortInfo {
    //    UUID getId();
    //
    //    String getTitle();
    //
    //    String getDescription();
    //
    //    String getImageId();
    //
    //    LocalDateTime getPublishedDate();
    private UUID id;
    private String title;
    private String description;
    private AttachResponseDTO image;
    private LocalDateTime publishedDate;

}
