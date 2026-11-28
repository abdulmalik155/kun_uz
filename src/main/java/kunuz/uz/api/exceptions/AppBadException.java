package kunuz.uz.api.exceptions;

public class AppBadException extends RuntimeException{

    public AppBadException(String badRequest){
        super(badRequest);
    }

    public AppBadException(Exception e) {
        super(e);
    }
}
