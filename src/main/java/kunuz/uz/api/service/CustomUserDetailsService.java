package kunuz.uz.api.service;

import kunuz.uz.api.dto.custom.CustomUserDetails;
import kunuz.uz.api.entity.ProfileEntity;
import kunuz.uz.api.repository.interfaces.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameWithRoles(username);
        if(optional.isEmpty())
            throw new UsernameNotFoundException(username);
        ProfileEntity profile = optional.get();
        return new CustomUserDetails(profile);
    }
}
