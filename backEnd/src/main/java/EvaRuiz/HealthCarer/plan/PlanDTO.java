package EvaRuiz.HealthCarer.plan;


import EvaRuiz.HealthCarer.medication.Medication;

import java.util.List;

public record PlanDTO(long id,
                      String name,
                      String startDate,
                      String endDate,
                      int distance,
                      Enum<PlanType> state,
                      List<Medication> medications) {
}
