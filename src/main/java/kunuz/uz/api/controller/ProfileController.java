package kunuz.uz.api.controller;

import jakarta.validation.Valid;
import kunuz.uz.api.dto.request.profile.filter.ProfileFilterDTO;
import kunuz.uz.api.dto.request.profile.ProfileRequestDTO;
import kunuz.uz.api.dto.request.profile.BaseProfileDTO;
import kunuz.uz.api.dto.response.profile.ProfileResponseDTO;
import kunuz.uz.api.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/profile")
public class ProfileController {

    //2. Profile
    //    6. Update Photo (ANY) (Murojat qilgan odamni rasmini upda qilish)   (remove old image)
    //           photo_id
    @Autowired
    private ProfileService service;

    @PostMapping(value = "")
    public ResponseEntity<ProfileResponseDTO> create(@Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping(value = "")
    public ResponseEntity<ProfileResponseDTO> update(@RequestParam(value = "profileId") Long id, @Valid @RequestBody ProfileRequestDTO dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ProfileResponseDTO> updateDetail(@RequestParam(value = "profileId") Long id, @Valid @RequestBody BaseProfileDTO dto){
        return ResponseEntity.ok(service.updateWithDetail(id, dto));
    }

    @GetMapping(value = "/paging")
    public ResponseEntity<Page<ProfileResponseDTO>> profileList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(service.profileList(page, size));
    }

    @DeleteMapping(value = "")
    public ResponseEntity<?> deleteById(@RequestParam(value = "profileId") Long id){
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<Page<ProfileResponseDTO>> filter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                           @RequestBody ProfileFilterDTO dto){
        return ResponseEntity.ok(service.filter(page,size, dto));
    }
}
