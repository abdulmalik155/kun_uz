package kunuz.uz.api.exceptions.category;

public class CategoryExistsException extends IllegalArgumentException {
    public CategoryExistsException(String categoryExists) {
        super(categoryExists);
    }
}
