package kunuz.uz.api.dto.request.region.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegionFilterDTO {

    private Integer id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Boolean visible;
    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;

}
