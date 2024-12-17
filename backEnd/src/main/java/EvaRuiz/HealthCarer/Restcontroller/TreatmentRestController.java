package EvaRuiz.HealthCarer.Restcontroller;

import EvaRuiz.HealthCarer.DTO.TreatmentDTO;
import EvaRuiz.HealthCarer.model.Treatment;
import EvaRuiz.HealthCarer.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/treatments")
public class TreatmentRestController {

    @Autowired
    private TreatmentService treatmentService;

    @GetMapping("/")
    public ResponseEntity<List<TreatmentDTO>> getTreatments() {
        List<TreatmentDTO> treatments = new ArrayList<>();
        for (Treatment treatment : treatmentService.findAll()) {
            treatments.add(new TreatmentDTO(treatment));
        }
        return ResponseEntity.ok(treatments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentDTO> getOneTreatment(@PathVariable long id) {
        Treatment treatment = treatmentService.getTreatment(id);
        TreatmentDTO treatmentDTO = new TreatmentDTO(treatment);
        return ResponseEntity.ok(treatmentDTO);
    }

    @PostMapping("/")
    public ResponseEntity<TreatmentDTO> createTreatment(@RequestBody TreatmentDTO treatmentDTO) {
        Treatment treatment = new Treatment(treatmentDTO);
        treatment = treatmentService.createTreatment(treatment);
        return ResponseEntity.ok(new TreatmentDTO(treatment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatment(@PathVariable long id) {
        treatmentService.deleteTreatment(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentDTO> updateTreatment(@PathVariable long id, @RequestBody TreatmentDTO treatmentDTO) {
        Treatment treatment = new Treatment(treatmentDTO);
        treatment = treatmentService.updateTreatment(id, new TreatmentDTO(treatment));
        return ResponseEntity.ok(new TreatmentDTO(treatment));
    }
}
