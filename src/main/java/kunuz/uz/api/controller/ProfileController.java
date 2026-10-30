package kunuz.uz.api.controller;

import jakarta.validation.Valid;
import kunuz.uz.api.dto.request.profile.*;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.mapper.profile.ProfileInfoDTO;
import kunuz.uz.api.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/profile")
public class ProfileController {

    @Autowired
    private ProfileService service;

    //ADMIN
    @PostMapping(value = "")
    public ResponseEntity<ProfileResponseDTO> create(@Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    //ADMIN
    @GetMapping(value = "/byId")
    public ResponseEntity<ProfileResponseDTO> getById(@RequestHeader(value = "profile-id")Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    //ADMIN
    @PutMapping(value = "")
    public ResponseEntity<ProfileResponseDTO> update(@RequestParam(value = "profileId") Long id,
                                                     @Valid @RequestBody ProfileUpdateDTO dto) {
        return ResponseEntity.ok(service.updateForAdmin(id, dto));
    }

    //ANY
    @PutMapping(value = "/update-any")
    public ResponseEntity<ProfileResponseDTO> updateDetail(@RequestParam(value = "profileId") Long id,
                                                           @Valid @RequestBody ProfileUpdateDetailDTO dto) {
        return ResponseEntity.ok(service.updateDetail(id, dto));
    }

    //ADMIN
    @GetMapping(value = "/paging")
    public ResponseEntity<Page<ProfileResponseDTO>> paging(@RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.paging(page, size));
    }

    //ADMIN
    @DeleteMapping(value = "")
    public ResponseEntity<?> deleteById(@RequestParam(value = "profileId") Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //ANY
    @PutMapping(value = "/password-update")
    public ResponseEntity<Boolean> updatePassword(@RequestHeader(value = "profile-id")Long id,
                                            @RequestBody ProfilePasswordUpdateDTO dto){
        return ResponseEntity.ok(service.updatePassword(id,dto));
    }

    //ADMIN
    @PostMapping(value = "/filter")
    public ResponseEntity<Page<ProfileInfoDTO>> filter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                                       @RequestBody ProfileFilterDTO dto) {
        return ResponseEntity.ok(service.filter(page, size, dto));
    }
}
