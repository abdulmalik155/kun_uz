package kunuz.uz.api.exceptions;

public class CategoryNotFoundException extends IllegalArgumentException{
    public CategoryNotFoundException(String s) {
        super(s);
    }
}
