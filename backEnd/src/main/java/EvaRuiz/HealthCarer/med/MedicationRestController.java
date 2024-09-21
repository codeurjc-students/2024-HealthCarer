package EvaRuiz.HealthCarer.med;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/meds")
public class MedicationRestController {

    @Autowired
    private MedicationService medicationService;
    public interface MedView extends Medication.BasicAtt{}

    @GetMapping("/")
    @JsonView(MedView.class)
    public List<Medication> getMeds() {
        return medicationService.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(MedView.class)
    public ResponseEntity<Medication> getOneMed(@PathVariable Long id) {
        return ResponseEntity.ok(medicationService.getMed(id));
    }

    @PostMapping("/")
    @JsonView(Medication.BasicAtt.class)
    public ResponseEntity<Medication> createMed(MedicationDTO medicationDTO) throws Exception {
        Medication createdMedication = medicationService.createMed(medicationDTO);

        URI location = fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdMedication.getId()).toUri();
        return ResponseEntity.created(location).body(createdMedication);
    }
    @PostMapping("/no-image")
    @JsonView(Medication.BasicAtt.class)
    public ResponseEntity<Medication> createMedNoImage(@RequestBody Medication medication) throws Exception{
        Medication createdMedication = medicationService.createMed(medication);

        URI location = fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdMedication.getId()).toUri();
        return ResponseEntity.created(location).body(createdMedication);
    }

    @PutMapping("/")
    @JsonView(Medication.BasicAtt.class)
    public ResponseEntity<Medication> updateMed(@RequestBody Medication medication) throws Exception{
        Medication createdMedication = medicationService.updateMed(medication);
        return ResponseEntity.ok(createdMedication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMed(@PathVariable Long id) {
        Medication medication = medicationService.getMed(id);
        medicationService.deleteMed(medication);
        return ResponseEntity.ok("Medication deleted");
    }



}
