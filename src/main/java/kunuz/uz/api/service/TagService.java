package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.TagRequestDTO;
import kunuz.uz.api.dto.response.TagResponseDTO;
import kunuz.uz.api.entity.TagEntity;
import kunuz.uz.api.repository.interfaces.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository repository;

    public void create(TagRequestDTO dto) {
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        repository.save(entity);
    }

    public List<TagResponseDTO> list() {
        List<TagResponseDTO> list = new LinkedList<>();
        for (TagEntity entity : repository.findAll()) {
            TagResponseDTO dto = new TagResponseDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            list.add(dto);
        }
        return list;
    }
}
