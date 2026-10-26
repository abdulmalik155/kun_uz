package kunuz.uz.api.dto.request.articleType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleTypeRequestDTO {

    @NotNull(message = "Order number is required")
    @Min(value = 0, message = "Minimum value for order is 0")
    private Integer orderNumber;

    @NotBlank(message = "Uzbek translation of article is required")
    @Size(min = 3, max = 255, message = "Minimum character length for article name is 3")
    private String nameUz;

    @NotBlank(message = "Russian translation of article is required")
    @Size(min = 3, max = 255, message = "Minimum character length for article name is 3")
    private String nameRu;

    @NotBlank(message = "English translation of article is required")
    @Size(min = 3, max = 255, message = "Minimum character length for article name is 3")
    private String nameEn;

}
