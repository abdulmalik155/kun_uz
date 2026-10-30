package kunuz.uz.api.service;

import kunuz.uz.api.dto.request.profile.*;
import kunuz.uz.api.dto.response.ProfileResponseDTO;
import kunuz.uz.api.entity.ProfileEntity;
import kunuz.uz.api.entity.ProfileRoleEntity;
import kunuz.uz.api.enums.ProfileRole;
import kunuz.uz.api.enums.ProfileStatus;
import kunuz.uz.api.exceptions.profile.PasswordMismatchException;
import kunuz.uz.api.exceptions.profile.ProfileExistsException;
import kunuz.uz.api.exceptions.profile.ProfileNotFoundException;
import kunuz.uz.api.mapper.profile.ProfileInfoDTO;
import kunuz.uz.api.repository.custom.classes.ProfileCustomRepository;
import kunuz.uz.api.repository.interfaces.ProfileRepository;
import kunuz.uz.api.util.PaginationUtil;
import kunuz.uz.api.util.ProfileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository repository;

    @Autowired
    private ProfileCustomRepository customRepository;

    @Autowired
    private ProfileUtil util;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ProfileRoleService roleService;

    public ProfileResponseDTO create(ProfileRequestDTO dto) {
        createCheck(dto);
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        entity.setVisible(true);
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setPassword(encoder.encode(dto.getPassword()));
        repository.save(entity);
        roleService.create(entity.getId(), dto.getRoleList());
        ProfileResponseDTO responseDTO = util.toDto(entity);
        return responseDTO;
    }

    private void createCheck(ProfileRequestDTO dto) {
        Optional<ProfileEntity> optionalExisting = repository.findByUsername(dto.getUsername().trim());
        if(optionalExisting.isPresent()){
            throw new ProfileExistsException("Profile already exists");
        }
        dto.getRoleList()
                .removeIf(profileRole ->
                        profileRole.equals(ProfileRole.ROLE_USER) || profileRole.equals(ProfileRole.ROLE_ADMIN));
        if(dto.getRoleList().isEmpty()){
            throw new IllegalArgumentException("Admin can not create another admin or user");
        }
    }

    public ProfileResponseDTO updateForAdmin(Long id, ProfileUpdateDTO newDto) {
        Optional<ProfileEntity> optionalExisting = repository.findByUsername(newDto.getUsername().trim());
        if(optionalExisting.isEmpty()){
            throw new ProfileNotFoundException("Profile doesn't exists");
        }
        ProfileEntity entity = getOrThrow(id);
        entity.setName(newDto.getName());
        entity.setSurname(newDto.getSurname());
        entity.setUsername(newDto.getUsername());
        repository.save(entity);
        roleService.merge(id, newDto.getRoleList());
        ProfileResponseDTO responseDTO = util.toDto(entity);
        responseDTO.setRoleList(newDto.getRoleList());
        return responseDTO;
    }

    public ProfileResponseDTO updateDetail(Long id, ProfileUpdateDetailDTO dto) {
        ProfileEntity entityToUpdate = getOrThrow(id);
        entityToUpdate.setName(dto.getName());
        entityToUpdate.setSurname(dto.getSurname());
        repository.save(entityToUpdate);
        return util.toDto(entityToUpdate);
    }

    private ProfileEntity getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(String.format("There is no profile with such id %s", id)));
    }

    public Page<ProfileResponseDTO> paging(int page, int size) {
        PaginationUtil.check(page, size);
        page--;
        PageRequest request = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ProfileEntity> entities = repository.findAllWithRole(request);
        return new PageImpl<>(util.toDtoList(entities.getContent()), entities.getPageable(), entities.getTotalElements());
    }

    public void deleteById(Long id) {
        repository.updateVisibleById(id);
    }

    public Page<ProfileInfoDTO> filter(int page, int size, ProfileFilterDTO dto) {
        PaginationUtil.check(page, size);
        page--;
        Page<ProfileEntity> entities = customRepository.filter(page, size, dto);
        return new PageImpl<>(toInfoList(entities.getContent()), entities.getPageable(), entities.getTotalElements());
    }

    private List<ProfileInfoDTO> toInfoList(Iterable<ProfileEntity> iterable){
        List<ProfileInfoDTO> list = new LinkedList<>();
        for (ProfileEntity entity : iterable) {
            ProfileInfoDTO dto = new ProfileInfoDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setSurname(entity.getSurname());
            dto.setUsername(entity.getUsername());
            dto.setRoleList(entity.getRoleList()
                    .stream()
                    .map(ProfileRoleEntity::getRole).toList());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setStatus(entity.getStatus());
            dto.setPhotoId(entity.getPhotoId());
            list.add(dto);
        }
        return list;
    }

    public ProfileResponseDTO getById(Long id) {
        ProfileResponseDTO dto = util.toDto(getOrThrow(id));
        dto.setRoleList(roleService.getAllByProfileId(id));
        return dto;
    }

    public Boolean updatePassword(Long id, ProfilePasswordUpdateDTO dto) {
        ProfileEntity entity = getOrThrow(id);
        if(!encoder.matches(dto.getCurrentPassword(), entity.getPassword())){
            throw new PasswordMismatchException("Wrong password");
        }
        entity.setPassword(encoder.encode(dto.getNewPassword().trim()));
        repository.save(entity);
        return true;
    }
}
