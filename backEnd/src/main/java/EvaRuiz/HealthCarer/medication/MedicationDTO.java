package EvaRuiz.HealthCarer.medication;


public record MedicationDTO(long id,
                     String name,
                     String boxImage,
                     String pillImage,
                     float stock,
                     String instructions,
                     float dose) {
}
