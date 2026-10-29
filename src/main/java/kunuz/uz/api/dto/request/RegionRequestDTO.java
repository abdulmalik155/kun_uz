package kunuz.uz.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionRequestDTO extends BaseRegionDTO {

    @NotNull(message = "Order number is required")
    @Min(value = 1, message = "Minimum value for order is 1")
    private Integer orderNumber;


}
