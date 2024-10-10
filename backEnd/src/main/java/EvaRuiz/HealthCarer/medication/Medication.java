package EvaRuiz.HealthCarer.medication;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Medication{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(BasicAtt.class)
    private Long id;
    @JsonView(BasicAtt.class)
    private String name;
    @JsonView(Medication.BasicAtt.class)
    private String boxImage;
    @JsonView(Medication.BasicAtt.class)
    private String pillImage;
    @JsonView(Medication.BasicAtt.class)
    private float stock;
    @JsonView(Medication.BasicAtt.class)
    private String instructions;
    @JsonView(Medication.BasicAtt.class)
    private float dose;

    @Override
    public String toString() {
        return "Medication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", boxImage='" + boxImage + '\'' +
                ", pillImage='" + pillImage + '\'' +
                ", stock=" + stock +
                ", instructions='" + instructions + '\'' +
                ", dose=" + dose +
                '}';
    }

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

    public Medication() {}

    public Medication(String name, float stock, String instructions, float dose) {
        this.name = name;
        this.stock = stock;
        this.instructions = instructions;
        this.dose = dose;
        this.boxImage = "";
        this.pillImage = "";
    }

    public interface BasicAtt {
    }
}
