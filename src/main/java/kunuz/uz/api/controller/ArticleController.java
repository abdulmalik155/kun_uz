package kunuz.uz.api.controller;

import kunuz.uz.api.dto.request.ArticleRequestDTO;
import kunuz.uz.api.dto.response.ArticleResponseDTO;
import kunuz.uz.api.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/article")
public class ArticleController {

    //   1. CREATE (Moderator) status(NotPublished)
    //        Request: (title,description,content,imageId, regionId, categoryList[{id},{id}], sectionList[{id},{id}] )
    //        Response: ArticleInfoDTO
    //    2. Update (Moderator (status to not publish)) (remove old image)
    //        Request: (title,description,content,imageId, regionId, categoryList[], sectionList[])
    //        Response: ArticleInfoDTO
    //    3. Delete Article (MODERATOR)
    //        Request: id
    //        Response: "Message"
    //    4. Change status by id (PUBLISHER) (publish,not_publish)
    //        Request: id, (status)
    //        Response: "Message"
    //    5. Get Last N Article By sectionId  ordered_by_created_date desc
    //        (Berilgan section (bo'lim) bo'yicha oxirgi N-ta published bo'lgan article ni return qiladi.)
    //         (Pagination bilan ishlaydigan qilish kerak)
    //        ArticleShortInfo
    //    6. Get Last 12 published Article except given id list ordered_by_created_date
    //        ([1,2,3,])
    //        (Berilgan Article-lardan tashqarida oxirgi qo'shilgan 12-ta article-ning return qiladi.
    //         (Article-larning id-lari kelasi, shu berilgan article-larndan tashqari bo'lgan article-larni 12-tasini return qilamiz.)
    //         (Pagination bilan ishlaydigan qilish kerak)
    //         ArticleShortInfo
    //   7. Get Last N Article by categoryId. (Berilgan categoryId bo'yicha eng oxirgi N-ta yangilikni return qiladi)
    //        (Pagination bilan ishlaydigan qilish kerak)
    //        ArticleShortInfo
    //    8. Get Last N Article by regionId. (Berilgan regionId bo'yicha eng oxirgi N-ta yangilikni return qiladi)
    //        (Pagination bilan ishlaydigan qilish kerak)
    //        ArticleFullInfo
    //    9. Get Article By Id And Lang
    //        ArticleFullInfo
    //    10. Get Last N Article By TagName (Berilgan tag nomi bo'yicha oxirgi N-ta yangilikni return qiladi)
    //        (Pagination bilan ishlaydigan qilish kerak)
    //        ArticleShortInfo
    //    11. Get Last 4 Article By sectionId, except given article id.
    //        (Berilgan SectionId bo'yicha oxirgi 4-ta maqolani return qiladi. Berilgan maqolaning id-sidan tash)
    //        ArticleShortInfo
    //    12. Get 4 most read articles, except given article id . (Ko'p oqilgan oxirgi 4-ta yangilikni return qiladi, berilgan maqoldagan tashqari)
    //        ArticleShortInfo
    //    13. Increase Article View Count by Article Id
    //          (article_id)
    //    14. Increase Share View Count by Article Id
    //             (article_id)
    //    15. Filter Article (title,region_id,category_id,published_date_from,published_date_to) with Pagination (PUBLISHER)
    //        (Bu API oddiy foydalanuvchilar uchun search/qidirish uchun ishlatiladi. Berilgan parameter-lari orqali search qilish mumkun. Bu api faqat PUBLISH bo'lgan maqolalarni retunr qiladi xalos.)
    //        ArticleShortInfo
    //    16. Filter Article for Moderators
    //       (title,region_id,category_id,crated_date_from,created_date_to
    //        published_date_from,published_date_to,status) with Pagination (MODERATOR)
    //       (Bu API faqat moderator uchun ishlaydi. Bu API faqat moderatorning maqolalarini return qiladi. Sear qilsa ham o'zining maqolalari orasidan search qiladi. Maqolaning statusi hohlagan bo'lishi mumkun.)
    //       ArticleShortInfo
    //    17. Filter Article for Moderators
    //         (title,region_id,category_id,crated_date_from,created_date_to
    //          published_date_from,published_date_to,moderator_id,publisher_id,status) with Pagination (PUBLISHER)
    //          (Bu API larcha Publisher-lar tamonidan ishlatiladi. Maqolaning statu-si har qadnay bo'lishi mumkun.)
    //          ArticleShortInfo
    //
    //    ArticleShortInfo
    //         id(uuid),title,description,image(id,url),published_date
    //    ArticleFullInfo
    //    id(uuid),title,description,content,shared_count,
    //        region(key,name),categoryList[{key,name},{}], sectionList[{id,name},{id,name}] published_date,view_count,like_count,
    //        tagList[name,name]
    //   1. CREATE (Moderator) status(NotPublished)
    //        Request: (title,description,content,imageId, regionId, categoryList[{id},{id}], sectionList[{id},{id}] )
    //        Response: ArticleInfoDTO
    @Autowired
    private ArticleService service;

    @PostMapping(value = "")
    public ResponseEntity<ArticleResponseDTO> create(@RequestBody ArticleRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping(value = "")
    public ResponseEntity<ArticleResponseDTO> update(@RequestHeader("id")UUID id,
                                                     @RequestBody ArticleRequestDTO dto){
        return ResponseEntity.ok(service.update(id, dto));
    }
    //    2. Update (Moderator (status to not publish)) (remove old image)
    //        Request: (title,description,content,imageId, regionId, categoryList[], sectionList[])
    //        Response: ArticleInfoDTO
    //    3. Delete Article (MODERATOR)
    //        Request: id
    //        Response: "Message"
    //    4. Change status by id (PUBLISHER) (publish,not_publish)
    //        Request: id, (status)
    //        Response: "Message"


}
