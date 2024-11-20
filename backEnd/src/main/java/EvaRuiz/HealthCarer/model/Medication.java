package EvaRuiz.HealthCarer.model;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.sql.Blob;

@Entity
@Getter
@Setter
@Table
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Lob
    @Column
    private Blob boxImage;

    @Column(nullable = false)
    private Float stock;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String instructions;

    @Column(nullable = false)
    private Float dose;

    public Medication() {}

    public Medication(String name, Blob boxImage, Float stock, String instructions, Float dose) {
        this.name = name;
        this.boxImage = boxImage;
        this.stock = stock;
        this.instructions = instructions;
        this.dose = dose;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBoxImage(Blob boxImage) {
        this.boxImage = boxImage;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setStock(Float stock) {
        this.stock = stock;
    }

    public void setDose(Float dose) {
        this.dose = dose;
    }

}
