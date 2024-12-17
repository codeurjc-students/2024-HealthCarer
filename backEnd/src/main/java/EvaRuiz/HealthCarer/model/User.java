package EvaRuiz.HealthCarer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String encodedPassword;

    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Treatment> treatments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Medication> medications = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Take> takes = new ArrayList<>();


    public User() {
    }


    public User(String name, String email, String encodedPassword, String... roles) {
        this.name = name;
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.roles = List.of(roles);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public List<Take> getTakes() {
        return takes;
    }

    public void setTakes(List<Take> takes) {
        this.takes = takes;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void addTreatment(Treatment treatment) {
        if (treatments == null) {
            treatments = new ArrayList<>();
        }
        if (!treatments.contains(treatment)) {
            treatments.add(treatment);
            treatment.setUser(this);
        }
    }

    public void removeTreatment(Treatment treatment) {
        if (treatments != null && treatments.contains(treatment)) {
            treatments.remove(treatment);
            if (treatment.getUser().equals(this)) {
                treatment.setUser(null);
            }
        }
    }

    public void addMedication(Medication medication) {
        if (medications == null) {
            medications = new ArrayList<>();
        }
        if (!medications.contains(medication)) {
            medications.add(medication);
            medication.setUser(this);
        }
    }

    public void removeMedication(Medication medication) {
        if (medications != null && medications.contains(medication)) {
            medications.remove(medication);
            if (medication.getUser().equals(this)) {
                medication.setUser(null);
            }
        }
    }

    public void addTake(Take take) {
        if (takes == null) {
            takes = new ArrayList<>();
        }
        if (!takes.contains(take)) {
            takes.add(take);
            take.setUser(this);
        }
    }

    public void removeTake(Take take) {
        if (takes != null && takes.contains(take)) {
            takes.remove(take);
            if (take.getUser().equals(this)) {
                take.setUser(null);
            }
        }
    }
}
