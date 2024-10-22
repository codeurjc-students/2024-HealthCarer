package EvaRuiz.HealthCarer.plan;

import EvaRuiz.HealthCarer.medication.Medication;
import EvaRuiz.HealthCarer.medication.MedicationRestController;
import EvaRuiz.HealthCarer.user.User;
import EvaRuiz.HealthCarer.user.UserRestController;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(BasicAtt.class)
    private Long id;
    @JsonView(BasicAtt.class)
    private String name;
    @JsonView(BasicAtt.class)
    private Calendar startDate;
    @JsonView(BasicAtt.class)
    private Calendar endDate;
    @JsonView(BasicAtt.class)
    private int distance;
    @JsonView(BasicAtt.class)
    private Enum<PlanType> state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonView(User.BasicAtt.class)
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "plan_medication",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id"))
    @JsonView(Medication.BasicAtt.class)
    private List<Medication> medications;

    public Plan(String name, Calendar startDate, Calendar endDate, int distance, User user, List<Medication> medications) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.user = user;
        this.medications = medications;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public Plan() {
    }

    public Plan(String name, Calendar startDate, Calendar endDate, int distance) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
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

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Enum<PlanType> getState() {
        return state;
    }

    public void setState(Enum<PlanType> state) {
        this.state = state;
    }

    public interface BasicAtt {}
}
