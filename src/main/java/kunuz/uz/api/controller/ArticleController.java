package kunuz.uz.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/article")
public class ArticleController {
    //6. Article∂
    //    1. CREATE (Moderator) status(NotPublished)
    //        (title,description,content,image_id, region_id,category_id, articleType(array))
    //    2. Update (Moderator (status to not publish)) (remove old image)
    //        (title,description,content,shared_count,image_id, region_id,category_id)
    //    3. Delete Article (MODERATOR)
    //    4. Change status by id (PUBLISHER) (publish,not_publish)
    //    5. Get Last 5 Article By Types  ordered_by_created_date
    //        (Berilgan types bo'yicha oxirgi 5ta pubished bo'lgan article ni return qiladi.)
    //        ArticleShortInfo
    //    6. Get Last 3 Article By Types  ordered_by_created_date
    //        (Berilgan types bo'yicha oxirgi 3ta pubished bo'lgan article ni return qiladi.)
    //         ArticleShortInfo
    //    7. Get Last 8  Articles witch id not included in given list.
    //        ([1,2,3,])
    //        ArticleShortInfo
    //    8. Get Article By Id And Lang
    //        ArticleFullInfo
    //    9. Get Last 4 Article By Types and except given article id.
    //        ArticleShortInfo
    //    10. Get 4 most read articles
    //        ArticleShortInfo
}
