package kunuz.uz.api.util;

import java.util.List;

public interface MappingFromArray <Short>{
    List<Short> shortToResponseDtoList(List<Object[]> content);

}
