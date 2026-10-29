package kunuz.uz.api.exceptions.articleType;

public class SectionNotFoundException extends IllegalArgumentException{
    public SectionNotFoundException(String message){
        super(message);
    }
}
