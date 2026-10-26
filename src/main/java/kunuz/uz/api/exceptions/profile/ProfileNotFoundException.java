package kunuz.uz.api.exceptions.profile;

public class ProfileNotFoundException extends IllegalArgumentException {
    public ProfileNotFoundException(String message) {
        super(message);
    }
}
