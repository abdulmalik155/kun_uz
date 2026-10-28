package kunuz.uz.api.controller;

import jakarta.validation.Valid;
import kunuz.uz.api.dto.request.region.BaseRegionDTO;
import kunuz.uz.api.dto.request.region.RegionRequestDTO;
import kunuz.uz.api.dto.request.region.filter.RegionFilterDTO;
import kunuz.uz.api.dto.response.region.RegionResponseDTO;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.mapper.region.RegionShort;
import kunuz.uz.api.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/region")
public class RegionController {

    @Autowired
    private RegionService service;

    @PostMapping(value = "")
    public ResponseEntity<RegionResponseDTO> create(@Valid @RequestBody RegionRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping(value = "")
    public ResponseEntity<RegionResponseDTO> update(@RequestParam(value = "regionId") Integer id,
                                                        @Valid @RequestBody BaseRegionDTO dto){
        return ResponseEntity.ok(service.update(id,dto));
    }

    @DeleteMapping(value = "")
    public ResponseEntity<?> delete(@RequestParam(value = "regionId") Integer id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/paging")
    public ResponseEntity<Page<RegionResponseDTO>> paging(@RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(service.paging(page,size));
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<Page<RegionResponseDTO>> regionList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                           @RequestBody RegionFilterDTO dto){
        return ResponseEntity.ok(service.regionList(page,size,dto));
    }

    @GetMapping(value = "/getByLanguage")
    public ResponseEntity<Page<RegionShort>> getByLang(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                                       @RequestParam(value = "language", defaultValue = "UZ") Language language){
        return ResponseEntity.ok(service.getByLanguage(page,size,language));
    }


}
