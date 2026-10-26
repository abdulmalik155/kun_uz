package kunuz.uz.api.controller;

import kunuz.uz.api.dto.request.articleType.ArticleTypeRequestDTO;
import kunuz.uz.api.dto.response.article.ArticleTypeResponseDTO;
import kunuz.uz.api.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/articleType")
public class ArticleTypeController {

//3. ArticleType
//    5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
//        (id,key,name) (name ga tegishli name_.. dagi qiymat qo'yiladi.)

    @Autowired
    private ArticleTypeService service;

    @PostMapping(value = "")
    public ResponseEntity<ArticleTypeResponseDTO> create(@RequestBody ArticleTypeRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping(value = "")
    public ResponseEntity<ArticleTypeResponseDTO> updateById(@RequestParam(value = "articleTypeId") Integer id,
                                                             @RequestBody ArticleTypeRequestDTO dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping(value = "")
    public ResponseEntity<?> deleteById(@RequestParam(value = "articleTypeId") Integer id){
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "")
    public ResponseEntity<Page<ArticleTypeResponseDTO>> paging (@RequestParam(value = "page", defaultValue = "1") int page,
                                                                @RequestParam(value = "size", defaultValue = "2") int size){
        return ResponseEntity.ok(service.paging(page,size));
    }


}
