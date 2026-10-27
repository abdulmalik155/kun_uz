package kunuz.uz.api.util;

import java.util.List;

public interface BaseEntityUtil<Request, Response, Entity> {

    Entity toEntity(Request dto, Entity entity);

    Response toResponseDto(Entity entity);

    List<Response> toResponseDtoList(Iterable<Entity> iterable);
}
