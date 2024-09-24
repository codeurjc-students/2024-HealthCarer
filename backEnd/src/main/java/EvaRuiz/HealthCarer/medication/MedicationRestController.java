package EvaRuiz.HealthCarer.medication;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/meds")
public class MedicationRestController {

    @Autowired
    private MedicationService medicationService;

    public interface MedicationView extends Medication.BasicAtt{}

    @GetMapping("/")
    @JsonView(MedicationView.class)
    public List<Medication> getMeds() {
        return medicationService.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(MedicationView.class)
    public ResponseEntity<Medication> getOneMed(@PathVariable long id) {;
        return ResponseEntity.ok(medicationService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Medication> createMed(@RequestBody Medication medication) {
        medicationService.save(medication);

        URI location = fromCurrentRequest().path("/{id}")
                .buildAndExpand(medication.getId()).toUri();
        return ResponseEntity.created(location).body(medication);
    }
    @PostMapping("/{id}/images")
    public ResponseEntity<Object> uploadImage(@PathVariable long id,
                                              @RequestParam MultipartFile boxImageFile,
                                              @RequestParam MultipartFile pillImageFile) throws IOException {
        Medication medication = medicationService.getMedication(id);
            URI location = fromCurrentRequest().build().toUri();
            medication.setBoxImage(location.toString());
            medication.setPillImage(location.toString());
            medicationService.save(medication);
            return ResponseEntity.created(location).build();

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Medication> deleteMed(@PathVariable Long id) {
        Medication medication = medicationService.getMedication(id);
            medicationService.deleteById(id);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medication> replaceMed(@PathVariable Long id, @RequestBody Medication newMedication) {
        Optional<Medication> med = medicationService.findById(id);
        if (med.isPresent()) {
            newMedication.setId(id);
            medicationService.save(newMedication);
            return ResponseEntity.ok(newMedication);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
