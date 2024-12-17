package EvaRuiz.HealthCarer.DTO;

import EvaRuiz.HealthCarer.model.Treatment;

import java.util.Date;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;


public record TreatmentDTO(
        Long id,
        String name,
        Date startDate,
        Date endDate,
        int dispensingFrequency,
        SortedSet<MedicationDTO> medications
)implements Comparable<TreatmentDTO> {

    public TreatmentDTO(Treatment treatment) {
        this(
                treatment.getId(),
                treatment.getName(),
                treatment.getStartDate(),
                treatment.getEndDate(),
                treatment.getDispensingFrequency(),
                treatment.getMedications().stream().map(MedicationDTO::new).collect(Collectors.toCollection(TreeSet::new))
        );
    }

    @Override
    public int compareTo(TreatmentDTO o) {
        return this.id.compareTo(o.id);
    }
}
