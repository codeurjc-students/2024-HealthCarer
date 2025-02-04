package EvaRuiz.HealthCarer.model;

import EvaRuiz.HealthCarer.DTO.TreatmentDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table (name = "treatments")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Date startDate;

    private Date endDate;

    private int dispensingFrequency;


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    @JoinTable(
            name = "medication_treatment",
            joinColumns = @JoinColumn(name = "treatment_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id")
    )
    private List<Medication> medications = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    public Treatment() {
    }

    public Treatment(TreatmentDTO treatmentDTO) {
        this.id = treatmentDTO.id();
        this.name = treatmentDTO.name();
        this.startDate = treatmentDTO.startDate();
        this.endDate = treatmentDTO.endDate();
        this.dispensingFrequency = treatmentDTO.dispensingFrequency();
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartLocalDate(LocalDateTime startDate) {
        this.startDate = Date.from(startDate.atZone(Calendar.getInstance().getTimeZone().toZoneId()).toInstant());
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDispensingFrequency() {
        return dispensingFrequency;
    }

    public void setDispensingFrequency(int dispensingFrequency) {
        this.dispensingFrequency = dispensingFrequency;
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

    public LocalDateTime checkIntakeDates() {
        LocalDateTime initialDate = LocalDateTime.ofInstant(startDate.toInstant(), Calendar.getInstance().getTimeZone().toZoneId());
        LocalDateTime objectiveDate = initialDate.plusHours(dispensingFrequency);
        LocalDateTime now = LocalDateTime.now();
        long difference = ChronoUnit.MINUTES.between(objectiveDate, now);
        if(difference >= -5 && difference <= 0) {
            return objectiveDate;
        } else {
            return null;
        }

    }
}
