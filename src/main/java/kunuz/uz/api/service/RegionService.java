package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.region.BaseRegionDTO;
import kunuz.uz.api.dto.request.region.RegionRequestDTO;
import kunuz.uz.api.dto.request.region.filter.RegionFilterDTO;
import kunuz.uz.api.dto.response.profile.ProfileResponseDTO;
import kunuz.uz.api.dto.response.region.RegionResponseDTO;
import kunuz.uz.api.entity.ProfileEntity;
import kunuz.uz.api.entity.RegionEntity;
import kunuz.uz.api.enums.Language;
import kunuz.uz.api.exceptions.region.RegionNotFoundException;
import kunuz.uz.api.mapper.region.RegionShort;
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
        entityToUpdate = util.toEntity(dto, entityToUpdate);
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
//        repository.deleteById(id);
        RegionEntity existingEntity = getEntityOrThrow(id);
        existingEntity.setVisible(false);
        repository.save(existingEntity);
    }

    public Page<RegionResponseDTO> filter(int page, int size, RegionFilterDTO dto) {
        PaginationUtil.check(page,size);
        page--;
        Page<RegionEntity> entities = customRepository.filter(page,size,dto);
        List<RegionResponseDTO> responseDTOList = util.toResponseDtoList(entities.getContent());
        return new PageImpl<>(responseDTOList, entities.getPageable(), entities.getTotalElements());
    }

    public Page<RegionShort> getByLanguage(int page, int size, Language language) {
        PaginationUtil.check(page, size);
        page--;
        if (language == null ) throw new IllegalArgumentException("Language of name of region can not be empty");

        Page<Object[]> entities = customRepository.getByLanguage(page,size,language);

        return new PageImpl<>(util
                .shortToResponseDtoList(
                        entities.getContent()
                ),
                entities.getPageable(),
                entities.getTotalElements());
    }


    public Page<RegionResponseDTO> paging(int page, int size) {
        PaginationUtil.check(page, size);
        page--;
        PageRequest request = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<RegionEntity> entities = repository.findAll(request);
        return new PageImpl<>(util
                .toResponseDtoList(entities.getContent()),
                entities.getPageable(),
                entities.getTotalElements());
    }
}
