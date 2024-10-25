package EvaRuiz.HealthCarer.take;

import EvaRuiz.HealthCarer.user.User;

public record TakeDTO(long id,
                      String timestamp,
                      User user) {
}
