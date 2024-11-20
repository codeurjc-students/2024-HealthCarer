package EvaRuiz.HealthCarer.DTO;


import EvaRuiz.HealthCarer.model.User;

public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Long[] plans;
    private Long[] takes;


    public UserDTO() {
    }

    public UserDTO(Long id, Long[] takes, Long[] plans, String email, String password, String username) {
        this.id = id;
        this.takes = takes;
        this.plans = plans;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public UserDTO (User user) {
        this.id= user.getId();
        this.username=user.getName();
        this.email = user.getEmail();
        this.password = user.getEncodedPassword();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long[] getPlans() {
        return plans;
    }

    public void setPlans(Long[] plans) {
        this.plans = plans;
    }

    public Long[] getTakes() {
        return takes;
    }

    public void setTakes(Long[] takes) {
        this.takes = takes;
    }
}
