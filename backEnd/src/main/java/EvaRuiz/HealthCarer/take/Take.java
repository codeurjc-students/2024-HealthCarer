package EvaRuiz.HealthCarer.take;

import EvaRuiz.HealthCarer.medication.Medication;
import EvaRuiz.HealthCarer.medication.MedicationRestController;
import EvaRuiz.HealthCarer.user.User;
import EvaRuiz.HealthCarer.user.UserRestController;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
public class Take {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(BasicAtt.class)
    private Long id;
    @JsonView(BasicAtt.class)
    private Calendar timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(UserRestController.UserView.class)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "take_medication",
            joinColumns = @JoinColumn(name = "take_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id"))
    @JsonView(MedicationRestController.MedicationView.class)
    private List<Medication> medications;



    public Take() {
        timestamp = Calendar.getInstance();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
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

    public interface BasicAtt {}
}
