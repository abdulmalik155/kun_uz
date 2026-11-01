package kunuz.uz.api.exceptions;

public class ArticleExistsException extends IllegalArgumentException {
    public ArticleExistsException(String articleExists) {
        super(articleExists);
    }
}
