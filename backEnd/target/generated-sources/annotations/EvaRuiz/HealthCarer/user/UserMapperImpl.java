package EvaRuiz.HealthCarer.user;

import EvaRuiz.HealthCarer.plan.Plan;
import EvaRuiz.HealthCarer.take.Take;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-05T08:06:53+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        String email = null;
        List<Plan> plans = null;
        List<Take> takes = null;

        if ( user.getId() != null ) {
            id = user.getId();
        }
        name = user.getName();
        email = user.getEmail();
        List<Plan> list = user.getPlans();
        if ( list != null ) {
            plans = new ArrayList<Plan>( list );
        }
        List<Take> list1 = user.getTakes();
        if ( list1 != null ) {
            takes = new ArrayList<Take>( list1 );
        }

        UserDTO userDTO = new UserDTO( id, name, email, plans, takes );

        return userDTO;
    }

    @Override
    public User toDomain(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.id() );
        user.setName( userDTO.name() );
        user.setEmail( userDTO.email() );
        List<Plan> list = userDTO.plans();
        if ( list != null ) {
            user.setPlans( new ArrayList<Plan>( list ) );
        }
        List<Take> list1 = userDTO.takes();
        if ( list1 != null ) {
            user.setTakes( new ArrayList<Take>( list1 ) );
        }

        return user;
    }

    @Override
    public List<UserDTO> toDTOs(Collection<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( users.size() );
        for ( User user : users ) {
            list.add( toDTO( user ) );
        }

        return list;
    }
}
