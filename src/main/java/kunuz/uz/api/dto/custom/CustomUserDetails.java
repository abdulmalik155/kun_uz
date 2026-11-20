package kunuz.uz.api.dto.custom;

import kunuz.uz.api.entity.ProfileEntity;
import kunuz.uz.api.enums.ProfileRole;
import kunuz.uz.api.enums.ProfileStatus;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private List<ProfileRole> roleList;
    private ProfileStatus status;


    public CustomUserDetails(ProfileEntity profile) {
        this.id = profile.getId();
        this.name = profile.getName();
        this.surname = profile.getSurname();
        this.username = profile.getUsername();
        this.password = profile.getPassword();
        this.roleList = profile.roleEnums(profile.getRoleList());
        this.status = profile.getStatus();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new LinkedList<>();
        for (ProfileRole profileRole : roleList) {
            list.add(new SimpleGrantedAuthority(profileRole.name()));
        }
        return list;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(ProfileStatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
