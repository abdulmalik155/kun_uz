package kunuz.uz.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ArticleListDTO {
    private List<UUID> articleIds;
}
