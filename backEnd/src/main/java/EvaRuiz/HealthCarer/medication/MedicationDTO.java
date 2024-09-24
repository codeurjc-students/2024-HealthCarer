package EvaRuiz.HealthCarer.medication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MedicationDTO {
    private MultipartFile boxImage;
    private MultipartFile pillImage;
    private String name;
    private float stock;
    private String instructions;
    private float dose;
}
