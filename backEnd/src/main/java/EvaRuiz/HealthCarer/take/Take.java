package EvaRuiz.HealthCarer.take;

import EvaRuiz.HealthCarer.medication.Medication;
import EvaRuiz.HealthCarer.medication.MedicationRestController;
import EvaRuiz.HealthCarer.user.User;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Take {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(BasicAtt.class)
    private Long id;
    @JsonView(BasicAtt.class)
    private Calendar timestamp;

    @ManyToMany
    @JsonView(MedicationRestController.MedicationView.class)
    private Set<Medication> medications = new HashSet<>();

    @ManyToOne
    private User user;

    public Take() {}

    public Take(Long id, Calendar timestamp, Set<Medication> medications, User user) {
        this.id = id;
        this.timestamp = timestamp;
        this.medications = medications;
        this.user = user;
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

    public Set<Medication> getMedications() {
        return medications;
    }

    public void setMedications(Set<Medication> medications) {
        this.medications = medications;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public interface BasicAtt {}
}
