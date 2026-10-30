package kunuz.uz.api.mapper.profile;


import kunuz.uz.api.enums.ProfileRole;
import kunuz.uz.api.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProfileInfoDTO {
   private Long id;
   private String name;
   private String surname;
   private String username;
   private List<ProfileRole> roleList;
   private LocalDateTime createdDate;
   private ProfileStatus status;
   private Long photoId;
}
