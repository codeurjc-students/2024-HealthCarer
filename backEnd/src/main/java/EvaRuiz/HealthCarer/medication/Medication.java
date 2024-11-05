package EvaRuiz.HealthCarer.medication;

import EvaRuiz.HealthCarer.plan.Plan;
import EvaRuiz.HealthCarer.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Medication{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(BasicAtt.class)
    private Long id;
    @JsonView(BasicAtt.class)
    private String name;
    @JsonView(BasicAtt.class)
    private String boxImage;
    @JsonView(BasicAtt.class)
    private String pillImage;
    @JsonView(BasicAtt.class)
    private Float stock;
    @JsonView(BasicAtt.class)
    private String instructions;
    @JsonView(BasicAtt.class)
    private Float dose;

    @ManyToMany
    @JsonIgnore
    private Set<Plan> plans = new HashSet<>();

    @ManyToMany
    private Set<User> users = new HashSet<>();


    public Medication() {}

    public interface BasicAtt { }

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

    public String getBoxImage() {
        return boxImage;
    }

    public void setBoxImage(String boxImage) {
        this.boxImage = boxImage;
    }

    public String getPillImage() {
        return pillImage;
    }

    public void setPillImage(String pillImage) {
        this.pillImage = pillImage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Set<Plan> getPlans() {
        return plans;
    }

    public void setPlans(Set<Plan> plans) {
        this.plans = plans;
    }

    public Float getStock() {
        return stock;
    }

    public void setStock(Float stock) {
        this.stock = stock;
    }

    public Float getDose() {
        return dose;
    }

    public void setDose(Float dose) {
        this.dose = dose;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addPlan(Plan plan) {
        Set<Plan> plansCopy = new HashSet<>(plans);
        plansCopy.add(plan);
        plan.getMedications().add(this);
        setPlans(plansCopy);
    }

    public void removePlan(Plan plan) {
        Set<Plan> plansCopy = new HashSet<>(plans);
        plansCopy.remove(plan);
        plan.getMedications().remove(this);
        setPlans(plansCopy);
    }

}
