package kunuz.uz.api.exceptions.profile;

public class ProfileExistsException extends IllegalArgumentException {
    public ProfileExistsException(String profileAlreadyExists) {
        super(profileAlreadyExists);
    }
}
