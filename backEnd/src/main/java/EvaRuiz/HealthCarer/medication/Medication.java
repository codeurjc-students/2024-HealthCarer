package EvaRuiz.HealthCarer.medication;

import com.fasterxml.jackson.annotation.JsonView;
import javax.persistence.*;



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
    private float stock;
    @JsonView(BasicAtt.class)
    private String instructions;
    @JsonView(BasicAtt.class)
    private float dose;

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

    public Medication(String name, float stock, String instructions, float dose) {
        this.name = name;
        this.stock = stock;
        this.instructions = instructions;
        this.dose = dose;
        this.boxImage = "";
        this.pillImage = "";
    }
}
