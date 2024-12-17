package EvaRuiz.HealthCarer.model;

import EvaRuiz.HealthCarer.DTO.TakeDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table (name = "takes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Take {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "medication_take",
            joinColumns = @JoinColumn(name = "take_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id")
    )
    @JsonManagedReference
    private List<Medication> medications = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    public Take() {
    }

    public Take(TakeDTO takeDTO) {
        this.id = takeDTO.id();
        this.date = takeDTO.date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addMedication(Medication medication) {
        if(medications == null) {
            medications = new ArrayList<>();
        }
        if (!medications.contains(medication)) {
            medications.add(medication);
            medication.getTakes().add(this);
        }
    }

    public void removeMedication(Medication medication) {
        if (medications != null && medications.contains(medication)) {
            medications.remove(medication);
            medication.getTakes().remove(this);
        }
    }
}
