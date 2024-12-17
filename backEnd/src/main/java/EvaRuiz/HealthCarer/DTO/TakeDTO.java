package EvaRuiz.HealthCarer.DTO;

import EvaRuiz.HealthCarer.model.Take;
import java.util.Date;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.TreeSet;


public record TakeDTO(
        Long id,
        Date date,
        SortedSet<MedicationDTO> medications
) implements Comparable<TakeDTO>
{


    public TakeDTO(Take take) {
        this(
                take.getId(),
                take.getDate(),
                take.getMedications().stream().map(MedicationDTO::new).collect(Collectors.toCollection(TreeSet::new))
        );
    }

    @Override
    public int compareTo(TakeDTO o) {
        return this.id.compareTo(o.id);
    }
}
