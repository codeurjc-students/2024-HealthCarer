package EvaRuiz.HealthCarer.DTO;

import EvaRuiz.HealthCarer.model.User;

public record UserPassDTO(Long id,
                          String name,
                          String email,
                          String password)
        implements Comparable<UserPassDTO>
{

    public UserPassDTO(User user) {
        this(user.getId(),
             user.getName(),
             user.getEmail(),
             user.getEncodedPassword());
    }

    @Override
    public int compareTo(UserPassDTO o) {
        return this.email.compareTo(o.email);
    }
}
