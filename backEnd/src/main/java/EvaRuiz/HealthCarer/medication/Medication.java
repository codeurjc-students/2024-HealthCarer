package EvaRuiz.HealthCarer.medication;

import EvaRuiz.HealthCarer.plan.Plan;
import EvaRuiz.HealthCarer.plan.PlanRestController;
import EvaRuiz.HealthCarer.user.User;
import EvaRuiz.HealthCarer.user.UserRestController;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.List;


@Entity
public class Medication{

    public Medication(Long id, String name, String s, String s1, float stock, String instructions, float dose, List<User> users, List<Plan> plans) {
        this.id = id;
        this.name = name;
        this.boxImage = s;
        this.pillImage = s1;
        this.stock = stock;
        this.instructions = instructions;
        this.dose = dose;
        this.users = users;
        this.plans = plans;
    }

    public Medication() {

    }

    public interface BasicAtt {
    }

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
    private float stock;
    @JsonView(BasicAtt.class)
    private String instructions;
    @JsonView(BasicAtt.class)
    private float dose;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_medication",
            joinColumns = @JoinColumn(name = "medication_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonView(UserRestController.UserView.class)
    private List<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "medication_plan",
            joinColumns = @JoinColumn(name = "medication_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_id"))
    @JsonView(PlanRestController.PlanView.class)
    private List<Plan> plans;

    public Medication(String name, Long id, String boxImage, String pillImage, float stock, String instructions, float dose) {
        this.name = name;
        this.id = id;
        this.boxImage = boxImage;
        this.pillImage = pillImage;
        this.stock = stock;
        this.instructions = instructions;
        this.dose = dose;
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

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public float getDose() {
        return dose;
    }

    public void setDose(float dose) {
        this.dose = dose;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    public Medication(String name, float stock, String instructions, float dose) {
        this.name = name;
        this.stock = stock;
        this.instructions = instructions;
        this.dose = dose;
        this.boxImage = "";
        this.pillImage = "";
        this.users = null;
        this.plans = null;
    }



}
