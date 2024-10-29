package EvaRuiz.HealthCarer.take;

import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TakeMapper {

        TakeDTO toDTO(Take take);

        Take toDomain(TakeDTO takeDTO);

        List<TakeDTO> toDTOs(Collection<Take> takes);
}
