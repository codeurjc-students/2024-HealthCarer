package EvaRuiz.HealthCarer.medication;

import EvaRuiz.HealthCarer.images.LocalImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;


import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@RestController
@RequestMapping("/api/medications")
public class MedicationRestController {
    private static final String MEDICATIONS_FOLDER = "medications";
    @Autowired
    private MedicationService medicationService;
    @Autowired
    private LocalImageService imageService;

    @PostConstruct
    public void init() {
        medicationService.createMedication(new Medication("Ibuprofeno", 20, "Tomar con agua", 1));
        medicationService.createMedication(new Medication("Paracetamol", 40, "Tomar con zumo", 3));
    }

    public interface MedicationView extends Medication.BasicAtt {
    }

    @GetMapping("/")
    public Collection<MedicationDTO> getMedications() {
        return medicationMapper.toDTOs(medicationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationDTO> getOneMedication(@PathVariable long id) {
        Medication medication = medicationService.getMedication(id);
        if (medication != null) {
            return ResponseEntity.ok(medicationMapper.toDTO(medication));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<MedicationDTO> createMedication(@RequestBody MedicationDTO medicationDTO) {
        Medication medication = medicationMapper.toDomain(medicationDTO);
        medicationService.createMedication(medication);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(medication.getId()).toUri();
        return ResponseEntity.created(location).body(medicationMapper.toDTO(medication));
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<Object> uploadImage(@PathVariable long id, @RequestParam MultipartFile boxImage, @RequestParam MultipartFile pillImage) throws IOException {
        Medication medication = medicationService.getMedication(id);
        Path box = imageService.saveImage(MEDICATIONS_FOLDER, imageService.getSize(), boxImage);
        Path pill = imageService.saveImage(MEDICATIONS_FOLDER, imageService.getSize(), pillImage);
        medication.setBoxImage(box.toString());
        medication.setPillImage(pill.toString());
        medicationService.replaceMedication(medication);
        return ResponseEntity.ok(medicationMapper.toDTO(medication));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicationDTO> replaceMedication(@PathVariable long id, @RequestBody MedicationDTO newMedicationDTO) {
        Medication medication = medicationService.getMedication(id);
        Medication newMedication = medicationMapper.toDomain(newMedicationDTO);
        newMedication.setId(medication.getId());
        newMedication = medicationService.replaceMedication(newMedication);
        return ResponseEntity.ok(medicationMapper.toDTO(newMedication));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MedicationDTO> deleteMedication(@PathVariable long id) throws IOException {
        Medication medication = medicationService.getMedication(id);
        medicationService.deleteMedication(medication);
        return ResponseEntity.ok(medicationMapper.toDTO(medication));
    }


}