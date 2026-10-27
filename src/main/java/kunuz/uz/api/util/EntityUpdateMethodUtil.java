package kunuz.uz.api.util;

public interface EntityUpdateMethodUtil<BaseRequest, Request, Response, Entity>  extends BaseEntityUtil<Request, Response, Entity> {

    Entity toEntityForUpdate(BaseRequest dto, Entity entity);
}
