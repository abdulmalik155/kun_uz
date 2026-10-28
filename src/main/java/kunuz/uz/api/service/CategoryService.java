package kunuz.uz.api.service;

import jakarta.validation.Valid;
import kunuz.uz.api.dto.request.category.CategoryRequestDTO;
import kunuz.uz.api.dto.response.category.CategoryResponseDTO;
import kunuz.uz.api.repository.custom.classes.CategoryCustomRepository;
import kunuz.uz.api.repository.interfaces.CategoryRepository;
import kunuz.uz.api.util.CategoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryCustomRepository customRepository;

    @Autowired
    private CategoryUtil util;

    public CategoryResponseDTO create(@Valid CategoryRequestDTO dto) {

        return null;
    }
}
