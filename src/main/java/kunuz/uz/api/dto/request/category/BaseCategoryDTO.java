package kunuz.uz.api.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseCategoryDTO {
    @NotBlank(message = "Uzbek translation of category is required")
    @Size(min = 3, max = 255, message = "Minimum character length for category name is 3")
    private String nameUz;

    @NotBlank(message = "Russian translation of category is required")
    @Size(min = 3, max = 255, message = "Minimum character length for category name is 3")
    private String nameRu;

    @NotBlank(message = "English translation of category is required")
    @Size(min = 3, max = 255, message = "Minimum character length for category name is 3")
    private String nameEn;
}
