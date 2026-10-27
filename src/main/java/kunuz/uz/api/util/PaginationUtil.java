package kunuz.uz.api.util;

public class PaginationUtil {
    public static void check(int page, int size){
        if(page <= 0){
            throw new IllegalArgumentException("This page doesn't exist");
        }
        if(size <= 0){
            throw new IllegalArgumentException("Invalid size info");
        }
    }
}
