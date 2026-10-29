package kunuz.uz.api.controller;

import kunuz.uz.api.dto.request.ArticleTypeRequestDTO;
import kunuz.uz.api.dto.request.ArticleTypeFilterDTO;
import kunuz.uz.api.dto.response.ArticleTypeResponseDTO;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.mapper.articleType.ArticleTypeShort;
import kunuz.uz.api.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/articleType")
public class ArticleTypeController {

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

    @GetMapping(value = "/articleTypeList")
    public ResponseEntity<Page<ArticleTypeResponseDTO>> articleTypeList (@RequestParam(value = "page", defaultValue = "1") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(service.articleTypeList(page,size));
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<Page<ArticleTypeResponseDTO>> filter (@RequestParam(value = "page", defaultValue = "1") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                                @RequestBody ArticleTypeFilterDTO dto){
        return ResponseEntity.ok(service.filter(page,size,dto));
    }

    @GetMapping(value = "/getByLanguage")
    public ResponseEntity<Page<ArticleTypeShort>> getByLang(@RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                                            @RequestParam(value = "language",defaultValue = "UZ") Language language){
        return ResponseEntity.ok(service.getByLanguage(page,size,language));
    }
}
