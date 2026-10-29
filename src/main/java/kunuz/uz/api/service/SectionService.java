package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.SectionRequestDTO;
import kunuz.uz.api.dto.response.SectionResponseDTO;
import kunuz.uz.api.entity.SectionEntity;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.exceptions.articleType.SectionExistsException;
import kunuz.uz.api.exceptions.articleType.SectionNotFoundException;
import kunuz.uz.api.mapper.articleType.SectionShort;
import kunuz.uz.api.repository.interfaces.SectionRepository;
import kunuz.uz.api.util.SectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    private SectionRepository repository;

    @Autowired
    private SectionUtil util;

    public SectionResponseDTO create(SectionRequestDTO newDto) {
        checkKeyAndOrder(newDto);
        SectionEntity entityToSave = util.toEntity(newDto, new SectionEntity());
        entityToSave.setVisible(true);
        SectionEntity savedEntity = repository.save(entityToSave);
        SectionResponseDTO responseDTO = util.toDto(savedEntity);
        return responseDTO;
    }

    public SectionResponseDTO update(Integer id, SectionRequestDTO newDto) {
        checkKeyAndOrder(id, newDto);
        SectionEntity existingEntity = util.toEntity(newDto, getEntityOrThrow(id));
        SectionEntity updatedEntity = repository.save(existingEntity);
        SectionResponseDTO responseDTO = util.toDto(updatedEntity);
        return responseDTO;
    }

    public void deleteById(Integer id) {
        repository.updateVisibleById(id);
    }

    public List<SectionResponseDTO> all() {
        List<SectionEntity> entities = repository.allSortedByOrderNumber();
        return util.toDtoList(entities);
    }

    public List<SectionShort> getByLanguage(Language language) {
        if (language == null) {
            throw new IllegalArgumentException("Language of name of section can not be empty");
        }
        List<SectionShort> entities = repository.getByLanguage(language.toString());
        return entities;
    }

    private void checkKeyAndOrder(SectionRequestDTO newDto) {
        Optional<SectionEntity> optionalKey = repository.findByKey(newDto.getKey());
        if (optionalKey.isPresent()) {
            throw new SectionExistsException("Section exists");
        }
        Optional<SectionEntity> optionalOrder = repository.findByOrderNumber(newDto.getOrderNumber());
        if (optionalOrder.isPresent()) {
            throw new IllegalArgumentException("This position is taken");
        }
    }

    private void checkKeyAndOrder(Integer id, SectionRequestDTO newDto) {
        Optional<SectionEntity> optionalKey = repository.findByKey(newDto.getKey());
        if (optionalKey.isPresent() && !optionalKey.get().getId().equals(id)) {
            throw new SectionExistsException("Section exists");
        }
        Optional<SectionEntity> optionalOrder = repository.findByOrderNumber(newDto.getOrderNumber());
        if (optionalOrder.isPresent() && !optionalOrder.get().getId().equals(id)) {
            throw new IllegalArgumentException("This position is taken");
        }
    }

    private SectionEntity getEntityOrThrow(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new SectionNotFoundException(String.format("Section with this id was not found %s", id)));
    }
}
