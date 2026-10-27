package kunuz.uz.api.dto.request.region;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseRegionDTO {

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
