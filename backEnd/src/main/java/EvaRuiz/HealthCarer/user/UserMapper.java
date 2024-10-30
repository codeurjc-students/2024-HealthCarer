package EvaRuiz.HealthCarer.user;


import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

        UserDTO toDTO(User user);

        User toDomain(UserDTO userDTO);

        List<UserDTO> toDTOs(Collection<User> users);
}
