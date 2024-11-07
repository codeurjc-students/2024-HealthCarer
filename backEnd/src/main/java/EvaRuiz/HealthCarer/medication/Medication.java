package EvaRuiz.HealthCarer.medication;

import javax.persistence.*;



@Entity
public class Medication{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String boxImage;

    private Float stock;

    @Column(length = 50000)
    private String instructions;

    private Float dose;

    public Medication() {}

    public Medication(String name, String boxImage, Float stock, String instructions, Float dose) {
        this.name = name;
        this.boxImage = boxImage;
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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Float getStock() {
        return stock;
    }

    public void setStock(Float stock) {
        this.stock = stock;
    }

    public Float getDose() {
        return dose;
    }

    public void setDose(Float dose) {
        this.dose = dose;
    }

}
