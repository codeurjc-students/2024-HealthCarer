package EvaRuiz.HealthCarer.medication;


public class MedicationDTO {
    private Long id;
    private String name;
    private String boxImage;
    private Float stock;
    private String instructions;
    private Float dose;

    public MedicationDTO() {
    }

    public MedicationDTO(Long id, String name, String boxImage, Float stock, String instructions, Float dose) {
        this.id = id;
        this.name = name;
        this.boxImage = boxImage;
        this.stock = stock;
        this.instructions = instructions;
        this.dose = dose;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public Float getStock() {
        return stock;
    }

    public void setStock(Float stock) {
        this.stock = stock;
    }

    public void setDose(Float dose) {
        this.dose = dose;
    }

    public Float getDose() {
        return dose;
    }

}
