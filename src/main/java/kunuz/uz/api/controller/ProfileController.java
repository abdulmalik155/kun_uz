package kunuz.uz.api.controller;

import jakarta.validation.Valid;
import kunuz.uz.api.dto.request.ProfileFilterDTO;
import kunuz.uz.api.dto.request.ProfileRequestDTO;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/profile")
public class ProfileController {
    //2. Profile
//    1. Create profile (ADMIN) (can create ROLE_MODERATOR, ROLE_PUBLISHER))
//         Request: name,surname,username(email/phone),password,status, roleList[]
//         Response: ProfileInfoDTO
//    2. Get By Id. (id bo'yicha bittasini return qiladi) (ADMIN)
//    3. Update Profile (ADMIN)
//    4. Update Profile Detail (ANY) (Profile updates own details)
//    5. Profile List (ADMIN) (Pagination)
//    6. Delete Profile By Id (ADMIN)
//    7. Update Photo (ANY) (Murojat qilgan odamni rasmini update qilish)   (remove old image)
//           photo_id
//    8. Update Password (ANY) (Murojat qilgan odamni password-ni update qilish)
//    9. Filter (query (name/surname/username),role,created_date_from,created_date_to) (ADMIN)
//        Response: ProfileInfoDTO
//
//     ProfileInfoDTO{id,name,surname,username,roleList['',''],createdDate,status,photo_id}
    @Autowired
    private ProfileService service;

    /**
     * Only for admin access
     *
     */
    @PostMapping(value = "")
    public ResponseEntity<ProfileResponseDTO> create(@Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    public ResponseEntity<ProfileResponseDTO> getById(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    //TODO do the get by id method
    /**
     * Only for admin access
     *
     */
    @PutMapping(value = "")
    public ResponseEntity<ProfileResponseDTO> update(@RequestParam(value = "profileId") Long id, @Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.ok(service.updateForAdmin(id, dto));
    }

    @PutMapping(value = "/updateForAnyUser")
    public ResponseEntity<ProfileResponseDTO> updateDetail(@RequestParam(value = "profileId") Long id, @Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.ok(service.updateWithDetail(id, dto));
    }

    /**
     * Only for admin access
     *
     */
    @GetMapping(value = "/paging")
    public ResponseEntity<Page<ProfileResponseDTO>> paging(@RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.paging(page, size));
    }

    /**
     * Only for admin access
     *
     */
    @DeleteMapping(value = "")
    public ResponseEntity<?> deleteById(@RequestParam(value = "profileId") Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<?> updatePassword(){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //TODO complete the update password method api

    @PostMapping(value = "/filter")
    public ResponseEntity<Page<ProfileResponseDTO>> filter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                           @RequestBody ProfileFilterDTO dto) {
        return ResponseEntity.ok(service.filter(page, size, dto));
    }
}
