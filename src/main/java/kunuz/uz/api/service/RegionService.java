package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.region.BaseRegionDTO;
import kunuz.uz.api.dto.request.region.RegionRequestDTO;
import kunuz.uz.api.dto.response.region.RegionResponseDTO;
import kunuz.uz.api.entity.RegionEntity;
import kunuz.uz.api.exceptions.region.RegionNotFoundException;
import kunuz.uz.api.repository.interfaces.RegionRepository;
import kunuz.uz.api.util.RegionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository repository;

    @Autowired
    private RegionUtil util;

    public RegionResponseDTO create(RegionRequestDTO dto) {
        RegionEntity entity = util.toEntity(dto, new RegionEntity());
        RegionEntity saved = repository.save(entity);
        RegionResponseDTO responseDTO = util.toResponseDto(saved);
        return responseDTO;
    }

    public RegionResponseDTO updateById(Integer id, BaseRegionDTO dto) {
        RegionEntity entityToUpdate =  getEntityOrThrow(id);
        entityToUpdate = util.toEntityForUpdate(dto, entityToUpdate);
        RegionEntity updated = repository.save(entityToUpdate);
        RegionResponseDTO responseDTO = util.toResponseDto(updated);
        return responseDTO;
    }

    private RegionEntity getEntityOrThrow(Integer id){
        return repository.findById(id)
                .orElseThrow(
                        ()-> new RegionNotFoundException(String.format("Region with given %s id doesn't exist", id)));
    }
}
