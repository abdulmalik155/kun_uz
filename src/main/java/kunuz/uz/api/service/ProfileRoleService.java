package kunuz.uz.api.service;

import kunuz.uz.api.entity.ProfileRoleEntity;
import kunuz.uz.api.enums.ProfileRole;
import kunuz.uz.api.repository.interfaces.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileRoleService {

    @Autowired
    private ProfileRoleRepository repository;

    public void create(Long profileId, List<ProfileRole> roleList) {
        for (ProfileRole role : roleList) {
            ProfileRoleEntity entity = new ProfileRoleEntity();
            entity.setProfileId(profileId);
            entity.setRole(role);
            repository.save(entity);
        }
    }

    public void merge(Long profileId, List<ProfileRole> newList) {
        List<ProfileRole> oldList = repository.findAllByProfileId(profileId);
        for (ProfileRole roles : newList) {
            if (!oldList.contains(roles)) {
                create(profileId, roles);
            }
        }
        for (ProfileRole profileRole : oldList) {
            if (!newList.contains(profileRole)) {
                deleteByIdAndRole(profileId, profileRole);
            }
        }
    }

    public void create(Long profileId, ProfileRole role) {
        ProfileRoleEntity entity = new ProfileRoleEntity();
        entity.setProfileId(profileId);
        entity.setRole(role);
        repository.save(entity);
    }

    public void deleteByIdAndRole(Long profileId, ProfileRole role) {
        repository.deleteByProfileIdAndRole(profileId, role);
    }

    public void deleteById(Long profileId) {
        repository.deleteByProfileId(profileId);
    }

    public List<ProfileRole> getAllByProfileId(Long id) {
        return repository.findAllByProfileId(id);
    }
}
