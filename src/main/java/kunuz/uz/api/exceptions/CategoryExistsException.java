package kunuz.uz.api.exceptions;

public class CategoryExistsException extends IllegalArgumentException {
    public CategoryExistsException(String categoryExists) {
        super(categoryExists);
    }
}
