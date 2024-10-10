package EvaRuiz.HealthCarer.medication;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/medications")
public class MedicationRestController {
    @Autowired
    private MedicationService medicationService;

    public interface MedicationView extends Medication.BasicAtt {
    }

    @GetMapping("/")
    @JsonView(MedicationView.class)
    public List<Medication> getMedications() {
        return medicationService.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(MedicationView.class)
    public ResponseEntity<Medication> getOneMedication(@PathVariable long id) {
        return new ResponseEntity<>(medicationService.getMedication(id), HttpStatus.OK);
    }

    @PostMapping("/")
    @JsonView(Medication.BasicAtt.class)
    public ResponseEntity<Medication> createMedication(MedicationDTO medicationDTO) {
        Medication newMedication = medicationService.createMedication(medicationDTO);
        return new ResponseEntity<>(newMedication, HttpStatus.CREATED);
    }

    @PostMapping("/no-image")
    @JsonView(Medication.BasicAtt.class)
    public ResponseEntity<Medication> createMedicationNoImage(@RequestBody Medication medication) {
        Medication newMedication = medicationService.createMedication(medication);
        return new ResponseEntity<>(newMedication, HttpStatus.CREATED);
    }

    @PutMapping("/")
    @JsonView(Medication.BasicAtt.class)
    public ResponseEntity<Medication> updateMedication(@RequestBody Medication medication) {
        Medication updatedMedication = medicationService.updateMedication(medication);
        return new ResponseEntity<>(updatedMedication, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Medication> deleteMedication(@PathVariable long id) {
        Medication medication = medicationService.getMedication(id);
        medicationService.deleteMedication(medication);
        return new ResponseEntity<>(medication, HttpStatus.OK);
    }


}