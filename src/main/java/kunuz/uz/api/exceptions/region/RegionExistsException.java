package kunuz.uz.api.exceptions.region;

public class RegionExistsException extends IllegalArgumentException {
    public RegionExistsException(String message) {
        super(message);
    }
}
