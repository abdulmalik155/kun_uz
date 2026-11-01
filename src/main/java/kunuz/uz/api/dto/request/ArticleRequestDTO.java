package kunuz.uz.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kunuz.uz.api.enums.Language;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "Image id is required")
    private String imageId;

    @NotNull(message = "Region id is required")
    private Integer regionId;

    @NotNull(message = "Read time is required")
    private Integer readTime;

    @NotEmpty(message = "Category id(s) is/are required ")
    private List<Integer> categoryList;

    @NotEmpty(message = "Section id(s) is/are required ")
    private List<Integer> sectionList;
}
