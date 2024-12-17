package EvaRuiz.HealthCarer.model;

import EvaRuiz.HealthCarer.DTO.MedicationDTO;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "medications")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Image image;

    @Column(nullable = false)
    private Float stock;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String instructions;

    @Column(nullable = false)
    private Float dose;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "medication_treatment",
            joinColumns = @JoinColumn(name = "medication_id"),
            inverseJoinColumns = @JoinColumn(name = "treatment_id"))
    private Set<Treatment> treatments = new HashSet<>();


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "medication_take",
            joinColumns = @JoinColumn(name = "medication_id"),
            inverseJoinColumns = @JoinColumn(name = "take_id")
    )
    private Set<Take> takes = new HashSet<>();


    public Medication() {
    }


    public Medication(MedicationDTO medicationDTO) {
        this.id = medicationDTO.id();
        this.name = medicationDTO.name();
        this.stock = medicationDTO.stock();
        this.instructions = medicationDTO.instructions();
        this.dose = medicationDTO.dose();
    }

    public Medication(String fakeMedication, int i, String fakeInstructions, float v) {
        this.name = fakeMedication;
        this.stock = (float) i;
        this.instructions = fakeInstructions;
        this.dose = v;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Float getStock() {
        return stock;
    }

    public void setStock(Float stock) {
        this.stock = stock;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Float getDose() {
        return dose;
    }

    public void setDose(Float dose) {
        this.dose = dose;
    }

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Take> getTakes() {
        return takes;
    }

    public void setTakes(Set<Take> takes) {
        this.takes = takes;
    }

    public void addTake(Take take) {
        takes.add(take);
        take.getMedications().add(this);
    }

    public void removeTake(Take take) {
        if (takes != null && takes.contains(take)) {
            takes.remove(take);
            take.getMedications().remove(this);
        }
    }

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
        treatment.getMedications().add(this);
    }

    public void removeTreatment(Treatment treatment) {
        if (treatments != null && treatments.contains(treatment)) {
            treatments.remove(treatment);
            treatment.getMedications().remove(this);
        }
    }
}
