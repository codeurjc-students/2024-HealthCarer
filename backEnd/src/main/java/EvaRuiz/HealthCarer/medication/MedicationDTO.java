package EvaRuiz.HealthCarer.medication;
import EvaRuiz.HealthCarer.plan.Plan;
import EvaRuiz.HealthCarer.user.User;


import java.util.List;

record MedicationDTO(Long id,
                     String name,
                     String boxImage,
                     String pillImage,
                     float stock,
                     String instructions,
                     float dose,
                     List<User> users,
                     List<Plan> plans) {





}
