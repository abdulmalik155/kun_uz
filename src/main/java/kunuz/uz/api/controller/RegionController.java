package kunuz.uz.api.controller;

import jakarta.validation.Valid;
import kunuz.uz.api.dto.request.region.BaseRegionDTO;
import kunuz.uz.api.dto.request.region.RegionRequestDTO;
import kunuz.uz.api.dto.response.region.RegionResponseDTO;
import kunuz.uz.api.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/region")
public class RegionController {

    @Autowired
    private RegionService service;

    //4. Region
    //    1. Create  (ADMIN)
    //        (order_number,name_uz, name_ru, name_en)
    //    2. Update by id (ADMIN)
    //        (key,name_uz, name_ru, name_en)
    //    3. Delete by id (ADMIN)
    //    4. Get List (ADMIN) (Pagination)
    //        (id,key,name_uz, name_ru, name_en,visible,created_date)
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





}
