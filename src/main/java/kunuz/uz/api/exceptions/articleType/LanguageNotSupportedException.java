package kunuz.uz.api.exceptions.articleType;

public class LanguageNotSupportedException extends IllegalArgumentException {
    public LanguageNotSupportedException(String message) {
        super(message);
    }
}
