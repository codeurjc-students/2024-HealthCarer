package EvaRuiz.HealthCarer.medication;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicationMapper{

        MedicationDTO toDTO(Medication medication);
        List<MedicationDTO> toDTOs(Collection<Medication> medications);
        Medication toDomain(MedicationDTO medicationDTO);
}
