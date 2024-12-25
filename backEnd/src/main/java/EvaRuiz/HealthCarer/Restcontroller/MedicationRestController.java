package EvaRuiz.HealthCarer.Restcontroller;

import EvaRuiz.HealthCarer.DTO.MedicationDTO;


import EvaRuiz.HealthCarer.model.LoggedUser;
import EvaRuiz.HealthCarer.model.User;
import EvaRuiz.HealthCarer.service.ImageService;
import EvaRuiz.HealthCarer.service.MedicationService;
import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.service.TreatmentService;
import EvaRuiz.HealthCarer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;




@RestController
@RequestMapping("/api/medications")
public class MedicationRestController {
    @Autowired
    private MedicationService medicationService;
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private ImageService imageService;



    @GetMapping("/")
    public ResponseEntity<List<MedicationDTO>> getMedications() {
        loggedUser.setLoggedUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<MedicationDTO> medicationsDTO = new ArrayList<>();
        for (Medication medication : medicationService.getAllMedications()) {
            if (medication.getUser().equals(loggedUser.getLoggedUser())) {
                medicationsDTO.add(new MedicationDTO(medication));
            }
        }
        return ResponseEntity.ok(medicationsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationDTO> getOneMedication(@PathVariable long id) {
        Medication medication = medicationService.getMedicationById(id);
        MedicationDTO medicationDTO = new MedicationDTO(medication);
        return ResponseEntity.ok(medicationDTO);
    }

    @PostMapping("/")
    public ResponseEntity<MedicationDTO> createMedication(@RequestBody MedicationDTO medicationDTO) {
        Medication medication = new Medication(medicationDTO);
        medicationService.checkMedication(medication);
        medication = medicationService.createMedication(medication);
        return ResponseEntity.ok(new MedicationDTO(medication));
    }


    @PutMapping("/{id}")
    public ResponseEntity<MedicationDTO> replaceMedication(@PathVariable long id, @RequestBody MedicationDTO newMedicationDTO) {
        medicationService.checkMedication(new Medication(newMedicationDTO));
        Medication oldMedication = medicationService.getMedicationById(id);
        medicationService.assingMedicationProperties(oldMedication, newMedicationDTO.name(), newMedicationDTO.stock(), newMedicationDTO.instructions(), newMedicationDTO.dose());
        Medication newMedication = medicationService.updateMedication(id, oldMedication);
        return ResponseEntity.ok(new MedicationDTO(newMedication));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MedicationDTO> deleteMedication(@PathVariable long id) {
        Medication medication = medicationService.getMedicationById(id);
        medicationService.deleteMedication(id);
        return ResponseEntity.ok(new MedicationDTO(medication));
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<Object> addImage(@PathVariable long id, @RequestParam("image") MultipartFile image) {
        Medication medication = medicationService.getMedicationById(id);
        if (medication == null) {return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        try {
            medication.setImage(this.imageService.save(image));
            medicationService.updateMedication(id, medication);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Object> getImage(@PathVariable long id) {
        Medication aux = this.medicationService.getMedicationById(id);
        if (aux == null) {return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        try {
            return this.imageService.createResponseFromImage(aux.getImage().getId());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
