package EvaRuiz.HealthCarer.plan;

import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanMapper {

        PlanDTO toDTO(Plan plan);

        Plan toDomain(PlanDTO planDTO);

        List<PlanDTO> toDTOs(Collection<Plan> plans);
}
