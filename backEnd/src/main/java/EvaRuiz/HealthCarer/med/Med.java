package EvaRuiz.HealthCarer.med;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;
import lombok.ToString;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Med {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String boxImage;
    private String pillImage;
    private float stock;
    private String instructions;
    private float dose;


    public Med() {}

    public Med( String name, float stock, String instructions, float dose) {
        this.name = name;
        this.stock = stock;
        this.instructions = instructions;
        this.dose = dose;
    }
}
