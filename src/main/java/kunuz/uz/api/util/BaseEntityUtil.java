package kunuz.uz.api.util;

import java.util.List;

public interface BaseEntityUtil<RequestDTO, ResponseDTO, Entity> {

    Entity toEntity(RequestDTO dto, Entity entity);

    ResponseDTO toResponseDto(Entity entity);

    List<ResponseDTO> toResponseDtoList(Iterable<Entity> iterable);
}
