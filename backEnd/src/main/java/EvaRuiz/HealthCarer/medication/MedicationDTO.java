package EvaRuiz.HealthCarer.medication;


public class MedicationDTO {
    private Long id;
    private String name;
    private String boxImage;
    private String pillImage;
    private Float stock;
    private String instructions;
    private Float dose;
    private Long[] plans;
    private Long[] users;

    public MedicationDTO() {
    }


    public MedicationDTO(Long id, String name, String boxImage, String pillImage, Float stock, String instructions, Float dose, Long[] plans, Long[] users) {
        this.id = id;
        this.name = name;
        this.boxImage = boxImage;
        this.pillImage = pillImage;
        this.stock = stock;
        this.instructions = instructions;
        this.dose = dose;
        this.plans = plans;
        this.users = users;
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

    public String getPillImage() {
        return pillImage;
    }

    public void setPillImage(String pillImage) {
        this.pillImage = pillImage;
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

    public Long[] getPlans() {
        return plans;
    }

    public void setPlans(Long[] plans) {
        this.plans = plans;
    }

    public Long[] getUsers() {
        return users;
    }

    public void setUsers(Long[] users) {
        this.users = users;
    }
}
