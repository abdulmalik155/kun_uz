package kunuz.uz.api.exceptions;

public class WrongTokenTypeException extends RuntimeException {
    public WrongTokenTypeException(String wrongTokenType) {
        super(wrongTokenType);
    }
}
