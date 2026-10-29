package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.RegionRequestDTO;
import kunuz.uz.api.dto.response.RegionResponseDTO;
import kunuz.uz.api.entity.RegionEntity;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.exceptions.region.RegionExistsException;
import kunuz.uz.api.exceptions.region.RegionNotFoundException;
import kunuz.uz.api.mapper.region.RegionShort;
import kunuz.uz.api.repository.interfaces.RegionRepository;
import kunuz.uz.api.util.RegionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository repository;

    @Autowired
    private RegionUtil util;

    public RegionResponseDTO create(RegionRequestDTO newDto) {
        checkKeyAndOrder(newDto);
        RegionEntity entity = util.toEntity(newDto, new RegionEntity());
        entity.setVisible(true);
        RegionEntity saved = repository.save(entity);
        RegionResponseDTO responseDTO = util.toDto(saved);
        return responseDTO;
    }

    public RegionResponseDTO update(Integer id, RegionRequestDTO dto) {
        checkKeyAndOrder(id, dto);
        RegionEntity entityToUpdate = util.toEntity(dto, getEntityOrThrow(id));
        RegionEntity updated = repository.save(entityToUpdate);
        RegionResponseDTO responseDTO = util.toDto(updated);
        return responseDTO;
    }

    public void delete(Integer id) {
        repository.updateVisibleById(id);
    }

    public List<RegionResponseDTO> all() {
        List<RegionEntity> entities = repository.allSortedByCreatedDate();
        return util.toDtoList(entities);
    }

    public List<RegionShort> getByLanguage(Language language) {
        if (language == null) {
            throw new IllegalArgumentException("Language of name of region is required");
        }
        List<RegionShort> entities = repository.getByLanguage(language.toString());
        return entities;
    }

    private RegionEntity getEntityOrThrow(Integer id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new RegionNotFoundException(String.format("Region with given %s id doesn't exist", id)));
    }

    private void checkKeyAndOrder(RegionRequestDTO newDto) {
        Optional<RegionEntity> optionalKey = repository.findByKey(newDto.getKey());
        if (optionalKey.isPresent()) {
            throw new RegionExistsException("Region exists");
        }
        Optional<RegionEntity> optionalOrder = repository.findByOrderNumber(newDto.getOrderNumber());
        if (optionalOrder.isPresent()) {
            throw new IllegalArgumentException("This position is taken");
        }
    }

    private void checkKeyAndOrder(Integer id, RegionRequestDTO newDto) {
        Optional<RegionEntity> optionalKey = repository.findByKey(newDto.getKey());
        if (optionalKey.isPresent() && !optionalKey.get().getId().equals(id)) {
            throw new RegionExistsException("Region exists");
        }
        Optional<RegionEntity> optionalOrder = repository.findByOrderNumber(newDto.getOrderNumber());
        if (optionalOrder.isPresent() && !optionalOrder.get().getId().equals(id)) {
            throw new IllegalArgumentException("This position is taken");
        }
    }
}
