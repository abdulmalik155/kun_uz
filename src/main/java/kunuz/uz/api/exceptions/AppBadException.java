package kunuz.uz.api.exceptions;

public class AppBadException extends RuntimeException{

    public AppBadException(String badRequest){
        super(badRequest);
    }
}
