package kunuz.uz.api.controller;

import jakarta.validation.Valid;
import kunuz.uz.api.dto.request.CategoryRequestDTO;
import kunuz.uz.api.dto.response.CategoryResponseDTO;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.mapper.category.CategoryShort;
import kunuz.uz.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping(value = "")
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping(value = "")
    public ResponseEntity<CategoryResponseDTO> update(@RequestParam(value = "id") Integer id,
                                                    @Valid @RequestBody CategoryRequestDTO dto){
        return ResponseEntity.ok(service.update(id,dto));
    }

    @DeleteMapping(value = "")
    public ResponseEntity<?> delete(@RequestParam(value = "id") Integer id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<CategoryResponseDTO>> all(){
        return ResponseEntity.ok(service.all());
    }

    @GetMapping(value = "/byLang")
    public ResponseEntity<List<CategoryShort>> allByLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language){
        return ResponseEntity.ok(service.allByLanguage(language));
    }

}
