package kunuz.uz.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ArticleResponseDTO {

    private UUID id;

    private String title;

    private String description;

    private String content;

    private String imageId;

    private Integer regionId;

    private List<CategoryResponseDTO> categoryList;

    private List<SectionResponseDTO> sectionList;

    private LocalDateTime createdDate;

    private List<TagResponseDTO> tagList;
}
