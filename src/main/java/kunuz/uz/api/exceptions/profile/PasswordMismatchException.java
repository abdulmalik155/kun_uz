package kunuz.uz.api.exceptions.profile;

public class PasswordMismatchException extends IllegalArgumentException {
    public PasswordMismatchException(String wrongPassword) {
        super(wrongPassword);
    }
}
