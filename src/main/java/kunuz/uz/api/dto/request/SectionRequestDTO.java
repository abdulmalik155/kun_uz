package kunuz.uz.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionRequestDTO {

    @NotBlank(message = "Uzbek translation of section is required")
    @Size(min = 3, max = 255, message = "Minimum character length for section name is 3")
    private String nameUz;

    @NotBlank(message = "Russian translation of section is required")
    @Size(min = 3, max = 255, message = "Minimum character length for section name is 3")
    private String nameRu;

    @NotBlank(message = "English translation of section is required")
    @Size(min = 3, max = 255, message = "Minimum character length for section name is 3")
    private String nameEn;

    @NotNull(message = "Order number is required")
    @Min(value = 1, message = "Minimum value for order is 1")
    private Integer orderNumber;

    @NotBlank(message = "Key is required")
    private String key;
}
