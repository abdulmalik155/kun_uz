package kunuz.uz.api.mapper.article;


import java.time.LocalDateTime;
import java.util.UUID;

public interface ArticleShortInfo {

    UUID getId();

    String getTitle();

    String getDescription();

    String getImageId();

    LocalDateTime getPublishedDate();

}
