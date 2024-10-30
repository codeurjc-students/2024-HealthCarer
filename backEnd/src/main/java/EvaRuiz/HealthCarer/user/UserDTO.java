package EvaRuiz.HealthCarer.user;


import EvaRuiz.HealthCarer.plan.Plan;
import EvaRuiz.HealthCarer.take.Take;

import java.util.List;

public record UserDTO(long id,
                      String name,
                      String email,
                      List<Plan> plans,
                      List<Take> takes) {

}
