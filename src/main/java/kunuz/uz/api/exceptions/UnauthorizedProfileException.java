package kunuz.uz.api.exceptions;

public class UnauthorizedProfileException extends RuntimeException {
    public UnauthorizedProfileException(String unauthorizedModerator) {
        super(unauthorizedModerator);
    }
}
