package kunuz.uz.api.dto.request.category.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CategoryFilterDTO {
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Boolean visible;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
}
