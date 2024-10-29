package EvaRuiz.HealthCarer.take;

import EvaRuiz.HealthCarer.medication.Medication;

import java.util.List;

public record TakeDTO(long id,
                      String timestamp,
                      List<Medication> medications) {
}
