package kunuz.uz.api.controller;


import jakarta.validation.Valid;
import kunuz.uz.api.dto.AuthorizationDTO;
import kunuz.uz.api.dto.request.RegistrationDTO;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(service.registration(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileResponseDTO> login(@Valid @RequestBody AuthorizationDTO dto) {
        return ResponseEntity.ok(service.login(dto));
    }

}
