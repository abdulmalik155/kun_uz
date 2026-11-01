package kunuz.uz.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ArticleTagDTO {

    @NotNull(message = "Article id is required")
    private UUID articleId;

    @NotBlank(message = "Tag name is required")
    private String name;
}
