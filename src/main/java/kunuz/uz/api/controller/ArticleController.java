package kunuz.uz.api.controller;

import kunuz.uz.api.dto.request.ArticleListDTO;
import kunuz.uz.api.dto.request.ArticleRequestDTO;
import kunuz.uz.api.dto.response.ArticleFullInfo;
import kunuz.uz.api.dto.response.ArticleResponseDTO;
import kunuz.uz.api.enums.ArticleStatus;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.mapper.article.ArticleShortInfo;
import kunuz.uz.api.service.ArticleService;
import kunuz.uz.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/article")
public class ArticleController {

    @Autowired
    private JwtUtil jwtUtil;
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
    @Autowired
    private ArticleService service;

    @PostMapping(value = "")
    public ResponseEntity<ArticleResponseDTO> create(@RequestHeader(value = "Authorization") String token,
                                                     @RequestBody ArticleRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(jwtUtil.moderatorId(token), dto));
    }

    @PutMapping(value = "")
    public ResponseEntity<ArticleResponseDTO> update(@RequestHeader(value = "Authorization") String token,
                                                     @RequestHeader("article-id") UUID articleId,
                                                     @RequestBody ArticleRequestDTO dto) {
        return ResponseEntity.ok(service.update(jwtUtil.moderatorId(token), articleId, dto));
    }

    @DeleteMapping(value = "")
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") String token,
                                    @RequestHeader("id") UUID id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(jwtUtil.moderatorId(token), id));
    }

    @PutMapping(value = "/status")
    public ResponseEntity<?> changeStatusById(@RequestHeader("article-id") UUID articleId,
                                              @RequestHeader(value = "Authorization") String token,
                                              @RequestParam("status") ArticleStatus status) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.changeStatusById(articleId, jwtUtil.publisherId(token), status));
    }

    @GetMapping(value = "/by-section")
    public ResponseEntity<Page<ArticleShortInfo>> getBySectionId(@RequestHeader(value = "section-id") Integer sectionId,
                                                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getBySectionId(page, size, sectionId));
    }

    @PostMapping(value = "/last-12")
    public ResponseEntity<Page<ArticleShortInfo>> getLast12Except(@RequestBody ArticleListDTO dto,
                                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                                  @RequestParam(value = "size", defaultValue = "12") int size) {
        return ResponseEntity.ok(service.getLast12Except(dto, page, size));
    }

    @GetMapping(value = "/by-category")
    public ResponseEntity<Page<ArticleShortInfo>> getByCategoryId(@RequestHeader(value = "category-id") Integer categoryId,
                                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getByCategoryId(page, size, categoryId));
    }

    @GetMapping(value = "/by-region")
    public ResponseEntity<Page<ArticleFullInfo>> getByRegionId(@RequestHeader(value = "region-id") Integer regionId,
                                                               @RequestParam(value = "page", defaultValue = "1") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getByRegionId(page, size, regionId));
    }

    @GetMapping(value = "/by-id-and-lang")
    public ResponseEntity<ArticleFullInfo> getByIdAndLang(@RequestHeader(value = "article-id") UUID articleId,
                                                                       @RequestHeader(value = "Accept-Language") Language language) {
        return ResponseEntity.ok(service.getByIdAndLang( articleId,language));
    }

    @GetMapping(value = "/by-tag-name")
    public ResponseEntity<Page<ArticleFullInfo>> getByTagName(@RequestHeader(value = "tag-name") String name,
                                                          @RequestParam(value = "page", defaultValue = "1") int page,
                                                          @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(service.getByTagName(page,size, name));
    }

    //    10. Get Last N Article By TagName (Berilgan tag nomi bo'yicha oxirgi N-ta yangilikni return qiladi)
    //        (Pagination bilan ishlaydigan qilish kerak)
    //        ArticleShortInfo

}
