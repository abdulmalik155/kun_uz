package kunuz.uz.api.dto.response.region;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegionResponseDTO {
    //  (id,key,name_uz, name_ru, name_en,visible,created_date)

    private Integer id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Boolean visible;
    private LocalDateTime createdDate;
}
