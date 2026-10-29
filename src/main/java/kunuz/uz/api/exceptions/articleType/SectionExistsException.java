package kunuz.uz.api.exceptions.articleType;

public class SectionExistsException extends IllegalArgumentException{
    public SectionExistsException(String message){
        super(message);
    }
}
