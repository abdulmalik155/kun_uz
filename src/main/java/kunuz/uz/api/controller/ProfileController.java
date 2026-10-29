package kunuz.uz.api.controller;

import jakarta.validation.Valid;
import kunuz.uz.api.dto.request.ProfileFilterDTO;
import kunuz.uz.api.dto.request.ProfileRequestDTO;
import kunuz.uz.api.dto.request.BaseProfileDTO;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/profile")
public class ProfileController {
//. Profile
//    1. Create profile (ADMIN) (can create MODERATOR,PUBLISHER))
//         (name,surname,email,phone,password,status,role)
//    2. Update Profile (ADMIN)
//    3. Update Profile Detail (ANY) (Profile updates own details)
//    4. Profile List (ADMIN) (Pagination)
//    5. Delete Profile By Id (ADMIN)
//    6. Update Photo (ANY) (Murojat qilgan odamni rasmini upda qilish)   (remove old image)
//           photo_id
//    7. Filter (name,surname,phone,role,created_date_from,created_date_to)
    //2. Profile
    //    6. Update Photo (ANY) (Murojat qilgan odamni rasmini upda qilish)   (remove old image)
    //           photo_id
    @Autowired
    private ProfileService service;

    /**
     * Only for admin access
     * */
    @PostMapping(value = "")
    public ResponseEntity<ProfileResponseDTO> create(@Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    /**
     * Only for admin access
     * */
    @PutMapping(value = "")
    public ResponseEntity<ProfileResponseDTO> update(@RequestParam(value = "profileId") Long id, @Valid @RequestBody ProfileRequestDTO dto){
        return ResponseEntity.ok(service.updateForAdmin(id, dto));
    }

    @PutMapping(value = "/updateForAnyUser")
    public ResponseEntity<ProfileResponseDTO> updateDetail(@RequestParam(value = "profileId") Long id, @Valid @RequestBody BaseProfileDTO dto){
        return ResponseEntity.ok(service.updateWithDetail(id, dto));
    }

    /**
     * Only for admin access
     * */
    @GetMapping(value = "/paging")
    public ResponseEntity<Page<ProfileResponseDTO>> paging(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(service.paging(page, size));
    }

    /**
     * Only for admin access
     * */
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
