package kunuz.uz.api.controller;

import kunuz.uz.api.dto.request.ArticleTagDTO;
import kunuz.uz.api.dto.response.ArticleTagResponseDTO;
import kunuz.uz.api.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class ArticleTagController {

    @Autowired
    private ArticleTagService service;

    @PostMapping(value = "")
    public ResponseEntity<ArticleTagResponseDTO> create(@RequestBody ArticleTagDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping(value = "")
    public ResponseEntity<List<ArticleTagResponseDTO>> list() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
