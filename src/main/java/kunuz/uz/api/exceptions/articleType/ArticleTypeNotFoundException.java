package kunuz.uz.api.exceptions.articleType;

public class ArticleTypeNotFoundException extends IllegalArgumentException{
    public ArticleTypeNotFoundException(String message){
        super(message);
    }
}
