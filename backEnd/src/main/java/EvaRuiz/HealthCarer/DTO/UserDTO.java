package EvaRuiz.HealthCarer.DTO;


import EvaRuiz.HealthCarer.model.User;

import java.util.List;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.stream.Collectors;

public record UserDTO(
        Long id,
        String name,
        String email,
        List<String> roles,
        SortedSet<MedicationDTO> medications,
        SortedSet<TreatmentDTO> treatments,
        SortedSet<TakeDTO> takes
) implements Comparable<UserDTO> {

    public UserDTO(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles(),
                user.getMedications().stream().map(MedicationDTO::new).collect(Collectors.toCollection(TreeSet::new)),
                user.getTreatments().stream().map(TreatmentDTO::new).collect(Collectors.toCollection(TreeSet::new)),
                user.getTakes().stream().map(TakeDTO::new).collect(Collectors.toCollection(TreeSet::new))
        );
    }

    @Override
    public int compareTo(UserDTO o) {
        return this.id.compareTo(o.id);
    }
}



