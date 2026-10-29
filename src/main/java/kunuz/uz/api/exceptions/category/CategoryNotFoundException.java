package kunuz.uz.api.exceptions.category;

public class CategoryNotFoundException extends IllegalArgumentException{
    public CategoryNotFoundException(String s) {
        super(s);
    }
}
