package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.ProfileFilterDTO;
import kunuz.uz.api.dto.request.ProfileRequestDTO;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.entity.ProfileEntity;
import kunuz.uz.api.exceptions.profile.ProfileNotFoundException;
import kunuz.uz.api.repository.custom.classes.ProfileCustomRepository;
import kunuz.uz.api.repository.interfaces.ProfileRepository;
import kunuz.uz.api.util.PaginationUtil;
import kunuz.uz.api.util.ProfileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository repository;

    @Autowired
    private ProfileCustomRepository customRepository;

    @Autowired
    private ProfileUtil util;

    public ProfileResponseDTO create(ProfileRequestDTO dto) {
        util.check(dto);
        ProfileEntity entity = util.toEntity(dto, new ProfileEntity());
        repository.save(entity);
        ProfileResponseDTO responseDTO = util.toDto(entity);
        return responseDTO;
    }

    public ProfileResponseDTO updateForAdmin(Long id, ProfileRequestDTO dto) {
        util.check(dto);
        return update(id, dto);
    }

    private ProfileResponseDTO update(Long id, ProfileRequestDTO dto) {
        ProfileEntity entityToUpdate = getEntityOrThrow(id);
        entityToUpdate = util.toEntity(dto, entityToUpdate);
        ProfileEntity updatedEntity = repository.save(entityToUpdate);
        ProfileResponseDTO responseDTO = util.toDto(updatedEntity);
        return responseDTO;
    }

    public ProfileResponseDTO updateWithDetail(Long id, ProfileRequestDTO dto) {
        return update(id, dto);
    }

    private ProfileEntity getEntityOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(String.format("There is no profile with such id %s", id)));
    }

    public Page<ProfileResponseDTO> paging(int page, int size) {
        PaginationUtil.check(page, size);
        page--;
        PageRequest request = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ProfileEntity> entities = repository.findAll(request);
        return new PageImpl<>(util.toDtoList(entities.getContent()), entities.getPageable(), entities.getTotalElements());
    }

    public void deleteById(Long id) {
//        repository.deleteById(id);
        ProfileEntity existingEntity = getEntityOrThrow(id);
        existingEntity.setVisible(false);
        repository.save(existingEntity);
    }

    public Page<ProfileResponseDTO> filter(int page, int size, ProfileFilterDTO dto) {
        PaginationUtil.check(page, size);
        page--;
        Page<ProfileEntity> entities = customRepository.filter(page, size, dto);
        return new PageImpl<>(util.toDtoList(entities.getContent()), entities.getPageable(), entities.getTotalElements());
    }
}
