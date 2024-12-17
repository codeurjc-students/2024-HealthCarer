package EvaRuiz.HealthCarer.DTO;


import EvaRuiz.HealthCarer.model.Medication;


public record MedicationDTO(
        Long id,
        String name,
        Float stock,
        String instructions,
        Float dose
) implements Comparable<MedicationDTO> {

    public MedicationDTO(Medication medication) {
        this(
                medication.getId(),
                medication.getName(),
                medication.getStock(),
                medication.getInstructions(),
                medication.getDose()
        );
    }

    @Override
    public int compareTo(MedicationDTO o) {
        return this.id.compareTo(o.id);
    }
}
