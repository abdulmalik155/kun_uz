package kunuz.uz.api.controller;

import kunuz.uz.api.dto.request.SectionRequestDTO;
import kunuz.uz.api.dto.response.SectionResponseDTO;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.mapper.articleType.SectionShort;
import kunuz.uz.api.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/section")
public class SectionController {

    @Autowired
    private SectionService service;

    @PostMapping(value = "")
    public ResponseEntity<SectionResponseDTO> create(@RequestBody SectionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping(value = "")
    public ResponseEntity<SectionResponseDTO> update(@RequestParam(value = "id") Integer id,
                                                     @RequestBody SectionRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping(value = "")
    public ResponseEntity<?> delete(@RequestParam(value = "id") Integer id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<SectionResponseDTO>> all() {
        return ResponseEntity.ok(service.all());
    }

    @GetMapping(value = "/byLang")
    public ResponseEntity<List<SectionShort>> getByLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        return ResponseEntity.ok(service.getByLanguage(language));
    }
}
