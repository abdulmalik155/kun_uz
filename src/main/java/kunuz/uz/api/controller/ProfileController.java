package kunuz.uz.api.controller;

import jakarta.validation.Valid;
import kunuz.uz.api.dto.request.ProfileRequestDTO;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "profile")
public class ProfileController {

    //2. Profile
    //    1. Create profile (ADMIN) (can create MODERATOR,PUBLISHER))
    //         (name,surname,email,phone,password,status,role)
    //    2. Update Profile (ADMIN)
    //    3. Update Profile Detail (ANY) (Profile updates own details)
    //    4. Profile List (ADMIN) (Pagination)
    //    5. Delete Profile By Id (ADMIN)
    //    6. Update Photo (ANY) (Murojat qilgan odamni rasmini upda qilish)   (remove old image)
    //           photo_id
    //    7. Filter (name,surname,phone,role,created_date_from,created_date_to)
    @Autowired
    private ProfileService service;

    @PostMapping(value = "")
    public ResponseEntity<ProfileResponseDTO> create(@Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }


}
