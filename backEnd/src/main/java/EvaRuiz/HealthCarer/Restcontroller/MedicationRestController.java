package EvaRuiz.HealthCarer.Restcontroller;

import EvaRuiz.HealthCarer.DTO.MedicationDTO;
import EvaRuiz.HealthCarer.service.LocalImageService;


import EvaRuiz.HealthCarer.service.MedicationService;
import EvaRuiz.HealthCarer.model.Medication;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;




@RestController
@RequestMapping("/api/medications")
public class MedicationRestController {
    @Autowired
    private MedicationService medicationService;
    @Autowired
    private LocalImageService imageService;



    @GetMapping("/")
    public ResponseEntity<List<MedicationDTO>> getMedications() {
        List<MedicationDTO> medications = new ArrayList<>();
        for (Medication medication : medicationService.findAll()) {
            medications.add(medicationService.toDTO(medication));
        }
        return ResponseEntity.ok(medications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationDTO> getOneMedication(@PathVariable long id) {
        Medication medication = medicationService.getMedication(id);
        MedicationDTO medicationDTO = medicationService.toDTO(medication);
        return ResponseEntity.ok(medicationDTO);
    }

    @PostMapping("/")
    public ResponseEntity<MedicationDTO> createMedication(@RequestBody MedicationDTO medicationDTO) {
        Medication medication = medicationService.toEntity(medicationDTO);
        medicationService.checkMedication(medication);
        medication = medicationService.createMedication(medication);
        medicationDTO = medicationService.toDTO(medication);
        return ResponseEntity.ok(medicationDTO);
    }



    @PostMapping("/{id}/images")
    public ResponseEntity<MedicationDTO> uploadImage(@PathVariable long id, @RequestParam MultipartFile boxImage) throws IOException {
        try {
            Medication medication = medicationService.getMedication(id);

            // Pass the image to a Blob
            Blob boxImageBlob = new SerialBlob(boxImage.getBytes());
            medication.setBoxImage(boxImageBlob);

            medication = medicationService.replaceMedication(medication);
            MedicationDTO medicationDTO = medicationService.toDTO(medication);
            return ResponseEntity.ok(medicationDTO);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        } catch (SQLException e) {
            throw new RuntimeException("Error converting file to Blob", e);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<MedicationDTO> replaceMedication(@PathVariable long id, @RequestBody MedicationDTO newMedicationDTO) {
        medicationService.checkMedication(medicationService.toEntity(newMedicationDTO));
        Medication oldMedication = medicationService.getMedication(id);
        medicationService.assingMedicationProperties(oldMedication, newMedicationDTO.getName(), newMedicationDTO.getBoxImage(), newMedicationDTO.getStock(), newMedicationDTO.getInstructions(), newMedicationDTO.getDose());
        Medication newMedication = medicationService.replaceMedication(oldMedication);
        MedicationDTO medicationDTO = medicationService.toDTO(newMedication);

        return ResponseEntity.ok(medicationDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MedicationDTO> deleteMedication(@PathVariable long id) throws IOException {
        Medication medication = medicationService.getMedication(id);
        if (medication.getBoxImage() != null) {
            //imageService.deleteImage(medication.getBoxImage());
        }
        medicationService.deleteMedication(medication);
        MedicationDTO medicationDTO = medicationService.toDTO(medication);
        return ResponseEntity.ok(medicationDTO);
    }


}
