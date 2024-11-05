package EvaRuiz.HealthCarer.user;

import EvaRuiz.HealthCarer.medication.Medication;
import EvaRuiz.HealthCarer.medication.MedicationRestController;
import EvaRuiz.HealthCarer.plan.Plan;
import EvaRuiz.HealthCarer.plan.PlanRestController;
import EvaRuiz.HealthCarer.take.Take;
import EvaRuiz.HealthCarer.take.TakeRestController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.HashSet;

import java.util.Set;

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

    @ManyToMany
    @JsonView(PlanRestController.PlanView.class)
    private Set<Plan> plans = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JsonView(TakeRestController.TakeView.class)
    private Set<Take> takes = new HashSet<>();

    @ManyToMany
    @JsonView(MedicationRestController.MedicationView.class)
    private Set<Medication> medications = new HashSet<>();


    public User() {
    }

    public User(Long id, Set<Medication> medications, Set<Take> takes, Set<Plan> plans, String email, String encodedPassword, String name) {
        this.id = id;
        this.medications = medications;
        this.takes = takes;
        this.plans = plans;
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.name = name;
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

    public Set<Plan> getPlans() {
        return plans;
    }

    public void setPlans(Set<Plan> plans) {
        this.plans = plans;
    }

    public Set<Take> getTakes() {
        return takes;
    }

    public void setTakes(Set<Take> takes) {
        this.takes = takes;
    }

    public void addPlan(Plan plan) {
        this.plans.add(plan);
    }

    public Set<Medication> getMedications() {
        return medications;
    }

    public void setMedications(Set<Medication> medications) {
        this.medications = medications;
    }

    public void addTake(Take take) {
        this.takes.add(take);
        take.setUser(this);
    }

    public void removePlan(Plan plan) {
        Set<Plan> plans = this.getPlans();
        plans.remove(plan);
        this.setPlans(plans);
    }

    public interface BasicAtt {}
}
