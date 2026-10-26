package kunuz.uz.api.exceptions;

public class ProfileNotFoundException extends IllegalArgumentException {
    public ProfileNotFoundException(String format) {
        super(format);
    }
}
