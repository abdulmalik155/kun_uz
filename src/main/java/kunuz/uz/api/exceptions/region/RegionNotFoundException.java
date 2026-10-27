package kunuz.uz.api.exceptions.region;

public class RegionNotFoundException extends IllegalArgumentException{
    public RegionNotFoundException(String message){
        super(message);
    }
}
