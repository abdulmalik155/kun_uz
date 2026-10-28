package kunuz.uz.api.controller;

import jakarta.validation.Valid;
import kunuz.uz.api.dto.request.category.CategoryRequestDTO;
import kunuz.uz.api.dto.request.category.filter.CategoryFilterDTO;
import kunuz.uz.api.dto.request.region.filter.RegionFilterDTO;
import kunuz.uz.api.dto.response.category.CategoryResponseDTO;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.mapper.category.CategoryShort;
import kunuz.uz.api.mapper.region.RegionShort;
import kunuz.uz.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {


    @Autowired
    private CategoryService service;
    //5. Category
    //    1. Create  (ADMIN)
    //        (order_number,name_uz, name_ru, name_en)
    //    2. Update by id (ADMIN)
    //        (order_number,name_uz, name_ru, name_en)
    //    3. Delete by id (ADMIN)
    //    4. Get List (ADMIN) - order by order_number (Pagination)
    //        (id,order_number,name_uz, name_ru, name_en,visible,created_date)
    //    5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
    //        (id,order_number,name) (name ga tegishli name_... dagi qiymat qo'yiladi.)

    @PostMapping(value = "")
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }


    @PutMapping(value = "")
    public ResponseEntity<CategoryResponseDTO> update(@RequestParam(value = "regionId") Integer id,
                                                    @Valid @RequestBody CategoryRequestDTO dto){
        return ResponseEntity.ok(service.update(id,dto));
    }

    @DeleteMapping(value = "")
    public ResponseEntity<?> delete(@RequestParam(value = "regionId") Integer id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/categoryList")
    public ResponseEntity<Page<CategoryResponseDTO>> categoryList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                              @RequestParam(value = "size", defaultValue = "10") int size,
                                                              @RequestBody CategoryFilterDTO dto){
        return ResponseEntity.ok(service.categoryList(page,size,dto));
    }

    @GetMapping(value = "/getByLanguage")
    public ResponseEntity<Page<CategoryShort>> getByLang(@RequestParam(value = "page", defaultValue = "1") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                                         @RequestParam(value = "language", defaultValue = "UZ") Language language){
        return ResponseEntity.ok(service.getByLanguage(page,size,language));
    }

}
