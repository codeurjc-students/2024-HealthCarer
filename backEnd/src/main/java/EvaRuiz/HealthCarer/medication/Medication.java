package EvaRuiz.HealthCarer.medication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String boxImage;
    private String pillImage;
    private float stock;
    private String instructions;
    private float dose;


    public Medication() {}

    public Medication(String name, float stock, String instructions, float dose) {
        this.name = name;
        this.stock = stock;
        this.instructions = instructions;
        this.dose = dose;
    }

    public interface BasicAtt {}
}
