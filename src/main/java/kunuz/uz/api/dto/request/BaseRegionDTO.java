package kunuz.uz.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseRegionDTO {

    @NotBlank(message = "Uzbek translation of region is required")
    @Size(min = 3, max = 255, message = "Minimum character length for region name is 3")
    private String nameUz;

    @NotBlank(message = "Russian translation of region is required")
    @Size(min = 3, max = 255, message = "Minimum character length for region name is 3")
    private String nameRu;

    @NotBlank(message = "English translation of region is required")
    @Size(min = 3, max = 255, message = "Minimum character length for region name is 3")
    private String nameEn;
}
