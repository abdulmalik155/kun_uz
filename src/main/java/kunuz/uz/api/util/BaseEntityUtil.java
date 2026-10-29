package kunuz.uz.api.util;

import java.util.List;

public interface BaseEntityUtil<RequestDTO, ResponseDTO, Entity> {

    Entity toEntity(RequestDTO dto, Entity entity);

    ResponseDTO toDto(Entity entity);

    List<ResponseDTO> toDtoList(Iterable<Entity> iterable);
}
