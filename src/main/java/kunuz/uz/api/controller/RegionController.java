package kunuz.uz.api.controller;

import jakarta.validation.Valid;
import kunuz.uz.api.dto.request.RegionRequestDTO;
import kunuz.uz.api.dto.response.RegionResponseDTO;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.mapper.region.RegionShort;
import kunuz.uz.api.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/region")
public class RegionController {

    @Autowired
    private RegionService service;

    @PostMapping(value = "")
    public ResponseEntity<RegionResponseDTO> create(@Valid @RequestBody RegionRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping(value = "")
    public ResponseEntity<RegionResponseDTO> update(@RequestParam(value = "id") Integer id,
                                                        @Valid @RequestBody RegionRequestDTO dto){
        return ResponseEntity.ok(service.update(id,dto));
    }

    @DeleteMapping(value = "")
    public ResponseEntity<?> delete(@RequestParam(value = "id") Integer id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<RegionResponseDTO>> all(){
        return ResponseEntity.ok(service.all());
    }

    @GetMapping(value = "/byLang")
    public ResponseEntity<List<RegionShort>> allByLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language){
        return ResponseEntity.ok(service.getByLanguage(language));
    }


}
