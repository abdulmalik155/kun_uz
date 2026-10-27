package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.region.BaseRegionDTO;
import kunuz.uz.api.dto.request.region.RegionRequestDTO;
import kunuz.uz.api.dto.request.region.filter.RegionFilterDTO;
import kunuz.uz.api.dto.response.region.RegionResponseDTO;
import kunuz.uz.api.entity.RegionEntity;
import kunuz.uz.api.exceptions.region.RegionNotFoundException;
import kunuz.uz.api.repository.custom.classes.RegionCustomRepository;
import kunuz.uz.api.repository.interfaces.RegionRepository;
import kunuz.uz.api.util.PaginationUtil;
import kunuz.uz.api.util.RegionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository repository;

    @Autowired
    private RegionCustomRepository customRepository;

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

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public Page<RegionResponseDTO> getRegionList(int page, int size, RegionFilterDTO dto) {
        PaginationUtil.check(page,size);
        page--;
        Page<RegionEntity> entities = customRepository.findAll(page,size,dto);
        List<RegionResponseDTO> responseDTOList = util.toResponseDtoList(entities.getContent());
        return new PageImpl<>(responseDTOList, entities.getPageable(), entities.getTotalElements());
    }
}
