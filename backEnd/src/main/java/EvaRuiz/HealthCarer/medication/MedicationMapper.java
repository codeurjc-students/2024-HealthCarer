package EvaRuiz.HealthCarer.medication;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicationMapper{

    MedicationDTO toDTO(Medication medication);

    Medication toDomain(MedicationDTO medicationDTO);

    List<MedicationDTO> toDTOs(Collection<Medication> medications);
}
