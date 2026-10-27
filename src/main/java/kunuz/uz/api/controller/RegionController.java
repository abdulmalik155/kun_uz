package kunuz.uz.api.controller;

import jakarta.validation.Valid;
import kunuz.uz.api.dto.request.region.BaseRegionDTO;
import kunuz.uz.api.dto.request.region.RegionRequestDTO;
import kunuz.uz.api.dto.request.region.filter.RegionFilterDTO;
import kunuz.uz.api.dto.response.region.RegionResponseDTO;
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

    //4. Region
    //    5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
    //        (id,key,name) (name ga tegishli name_.. dagi qiymat qo'yiladi.) (visible true)

    @PostMapping(value = "")
    public ResponseEntity<RegionResponseDTO> create(@Valid @RequestBody RegionRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping(value = "")
    public ResponseEntity<RegionResponseDTO> updateById(@RequestParam(value = "regionId") Integer id,
                                                        @Valid @RequestBody BaseRegionDTO dto){
        return ResponseEntity.ok(service.updateById(id,dto));
    }

    @DeleteMapping(value = "")
    public ResponseEntity<?> deleteById(@RequestParam(value = "regionId") Integer id){
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/paging")
    public ResponseEntity<Page<RegionResponseDTO>> getRegionList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                           @RequestBody RegionFilterDTO dto){
        return ResponseEntity.ok(service.getRegionList(page,size,dto));
    }




}
