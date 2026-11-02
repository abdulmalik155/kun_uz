package kunuz.uz.api.controller;

import kunuz.uz.api.dto.request.TagRequestDTO;
import kunuz.uz.api.dto.response.TagResponseDTO;
import kunuz.uz.api.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/tag")
public class TagController {

    @Autowired
    private TagService service;

    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody TagRequestDTO dto) {
        service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "")
    public ResponseEntity<List<TagResponseDTO>> list() {
        return ResponseEntity.ok(service.list());
    }
}
