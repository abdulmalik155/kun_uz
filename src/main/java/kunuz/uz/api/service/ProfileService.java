package kunuz.uz.api.service;

import jakarta.validation.Valid;
import kunuz.uz.api.dto.request.profile.ProfileFilterDTO;
import kunuz.uz.api.dto.request.profile.ProfileRequestDTO;
import kunuz.uz.api.dto.request.profile.BaseProfileDTO;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.entity.ProfileEntity;
import kunuz.uz.api.exceptions.ProfileNotFoundException;
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

    public ProfileResponseDTO create(ProfileRequestDTO dto) {
        ProfileUtil.check(dto);
        ProfileEntity entity = ProfileUtil.toEntity(dto, new ProfileEntity());
        repository.save(entity);
        return ProfileUtil.toDto(entity);
    }

    public ProfileResponseDTO update(Long id, ProfileRequestDTO dto) {
        ProfileUtil.check(dto);
        ProfileEntity updatedEntity = repository.save(ProfileUtil.toEntity(dto, getEntityOrThrow(id)));
        return ProfileUtil.toDto(updatedEntity);
    }

    public ProfileResponseDTO updateDetail(Long id, @Valid BaseProfileDTO dto) {
        ProfileEntity updatedEntity = repository.save(ProfileUtil.toEntityDetail(dto, getEntityOrThrow(id)));
        return ProfileUtil.toDto(updatedEntity);
    }

    private ProfileEntity getEntityOrThrow(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(String.format("There is no profile with such id %s", id)));
    }

    public Page<ProfileResponseDTO> profileList(int page, int size) {
        PaginationUtil.check(page, size);
        page--;
        PageRequest request = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<ProfileEntity> entities = repository.findAll(request);
        return new PageImpl<>(ProfileUtil.toList(entities.getContent()), entities.getPageable(), entities.getTotalElements());
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Page<ProfileResponseDTO> filter(int page, int size, ProfileFilterDTO dto) {
        PaginationUtil.check(page,size);
        page--;
        Page<ProfileEntity> entities = customRepository.filter(page,size,dto);
        return new PageImpl<>(ProfileUtil.toList(entities.getContent()), entities.getPageable(), entities.getTotalElements());
    }
}
