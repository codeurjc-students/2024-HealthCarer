package EvaRuiz.HealthCarer.user;

import EvaRuiz.HealthCarer.medication.Medication;
import EvaRuiz.HealthCarer.plan.Plan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(BasicAtt.class)
    private Long id;
    @JsonView(BasicAtt.class)
    private String name;
    @JsonView(User.BasicAtt.class)
    private String email;
    @JsonIgnore
    private String encodedPassword;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_medication",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id"))
    @JsonView(Medication.BasicAtt.class)
    private List<Medication> medications;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_plan",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_id"))
    @JsonView(Plan.BasicAtt.class)
    private List<Plan> plans;

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    public User() {
    }

    public User(String name, String email, String encodedPassword) {
        this.name = name;
        this.email = email;
        this.encodedPassword = encodedPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }
    public interface BasicAtt {}
}
