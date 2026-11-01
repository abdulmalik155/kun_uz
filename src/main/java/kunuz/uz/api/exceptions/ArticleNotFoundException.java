package kunuz.uz.api.exceptions;

public class ArticleNotFoundException extends IllegalArgumentException {
    public ArticleNotFoundException(String articleNotFound) {
        super(articleNotFound);
    }
}
