package kunuz.uz.api.exceptions;

public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException(String wrongTokenType) {
        super(wrongTokenType);
    }
}
