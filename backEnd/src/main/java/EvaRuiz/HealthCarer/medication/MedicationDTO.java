package EvaRuiz.HealthCarer.medication;

import org.springframework.web.multipart.MultipartFile;


public class MedicationDTO {

    public MultipartFile getBoxImage() {
        return boxImage;
    }

    public void setBoxImage(MultipartFile boxImage) {
        this.boxImage = boxImage;
    }

    public MultipartFile getPillImage() {
        return pillImage;
    }

    public void setPillImage(MultipartFile pillImage) {
        this.pillImage = pillImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    private MultipartFile boxImage;
    private MultipartFile pillImage;
    private String name;
    private float stock;
    private String instructions;
    private float dose;
}
