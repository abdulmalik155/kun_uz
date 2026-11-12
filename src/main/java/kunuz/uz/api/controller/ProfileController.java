package kunuz.uz.api.controller;

import jakarta.validation.Valid;
import kunuz.uz.api.dto.request.profile.*;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.mapper.profile.ProfileInfoDTO;
import kunuz.uz.api.service.ProfileService;
import kunuz.uz.api.util.JwtUtil;
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

    @Autowired
    private JwtUtil jwtUtil;

    //ADMIN
    @PostMapping(value = "")
    public ResponseEntity<ProfileResponseDTO> create(@RequestHeader(value = "Authorization") String token,
                                                     @Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(jwtUtil.adminId(token), dto));
    }

    //ADMIN
    @GetMapping(value = "/by-id")
    public ResponseEntity<ProfileResponseDTO> getById(@RequestHeader(value = "Authorization") String token,
                                                      @RequestHeader(value = "id") Long id) {
        jwtUtil.adminId(token);
        return ResponseEntity.ok(service.getById(id));
    }

    //ADMIN
    @PutMapping(value = "")
    public ResponseEntity<ProfileResponseDTO> update(@RequestHeader(value = "Authorization") String token,
                                                     @RequestHeader(value = "profileId") Long profileId,
                                                     @Valid @RequestBody ProfileUpdateDTO dto) {
        jwtUtil.adminId(token);
        return ResponseEntity.ok(service.updateForAdmin(profileId, dto));
    }

    //ANY
    @PutMapping(value = "/update-any")
    public ResponseEntity<ProfileResponseDTO> updateDetail(@RequestHeader(value = "id") Long id,
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
    public ResponseEntity<?> deleteById(@RequestHeader(value = "id") Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //ANY
    @PutMapping(value = "/password")
    public ResponseEntity<Boolean> updatePassword(@RequestHeader(value = "id") Long id,
                                                  @RequestBody ProfilePasswordUpdateDTO dto) {
        return ResponseEntity.ok(service.updatePassword(id, dto));
    }

    //ADMIN
    @PostMapping(value = "/filter")
    public ResponseEntity<Page<ProfileInfoDTO>> filter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                                       @RequestBody ProfileFilterDTO dto) {
        return ResponseEntity.ok(service.filter(page, size, dto));
    }
}
