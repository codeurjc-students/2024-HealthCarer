package EvaRuiz.HealthCarer.Restcontroller;

import EvaRuiz.HealthCarer.DTO.MedicationDTO;
import EvaRuiz.HealthCarer.DTO.TreatmentDTO;
import EvaRuiz.HealthCarer.model.LoggedUser;
import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.model.Treatment;
import EvaRuiz.HealthCarer.service.MedicationService;
import EvaRuiz.HealthCarer.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/treatments")
public class TreatmentRestController {

    @Autowired
    private TreatmentService treatmentService;
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private MedicationService medicationService;

    @GetMapping("/")
    public ResponseEntity<List<TreatmentDTO>> getTreatments() {
        loggedUser.setLoggedUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<TreatmentDTO> treatments = new ArrayList<>();
        for (Treatment treatment : loggedUser.getLoggedUser().getTreatments()) {
            treatments.add(new TreatmentDTO(treatment));
        }
        return ResponseEntity.ok(treatments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentDTO> getOneTreatment(@PathVariable long id) {
        loggedUser.setLoggedUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Treatment treatment = treatmentService.getTreatment(id);
        if (treatment == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); }
        if (!loggedUser.getLoggedUser().getTreatments().contains(treatment)) { return ResponseEntity.status(HttpStatus.FORBIDDEN).build();}
        TreatmentDTO treatmentDTO = new TreatmentDTO(treatment);
        return ResponseEntity.ok(treatmentDTO);
    }

    @PostMapping("/")
    public ResponseEntity<TreatmentDTO> createTreatment(@RequestBody TreatmentDTO treatmentDTO) {
        loggedUser.setLoggedUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Treatment treatment = new Treatment(treatmentDTO);
        treatment.setUser(loggedUser.getLoggedUser());
        for (MedicationDTO medicationDTO : treatmentDTO.medications()) {
            Medication medication = medicationService.getMedicationById(medicationDTO.id());
            treatment.getMedications().add(medication);
        }
        treatment = treatmentService.createTreatment(treatment);
        return ResponseEntity.ok(new TreatmentDTO(treatment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatment(@PathVariable long id) {
        loggedUser.setLoggedUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Treatment treatment = treatmentService.getTreatment(id);
        if (treatment == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); }
        if (!loggedUser.getLoggedUser().getTreatments().contains(treatment)) { return ResponseEntity.status(HttpStatus.FORBIDDEN).build();}
        treatmentService.deleteTreatment(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentDTO> updateTreatment(@PathVariable long id, @RequestBody TreatmentDTO treatmentDTO) {
        loggedUser.setLoggedUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Treatment existingTreatment = treatmentService.getTreatment(id);
        if (existingTreatment == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); }
        if (!loggedUser.getLoggedUser().getTreatments().contains(existingTreatment)) { return ResponseEntity.status(HttpStatus.FORBIDDEN).build();}
        Treatment treatment = new Treatment(treatmentDTO);
        treatment = treatmentService.updateTreatment(id, new TreatmentDTO(treatment));
        return ResponseEntity.ok(new TreatmentDTO(treatment));
    }
}
