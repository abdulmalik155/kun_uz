package kunuz.uz.api.dto.request.category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDTO extends BaseCategoryDTO{
    @NotNull(message = "Order number is required")
    @Min(value = 0, message = "Minimum value for order is 0")
    private Integer orderNumber;
}
