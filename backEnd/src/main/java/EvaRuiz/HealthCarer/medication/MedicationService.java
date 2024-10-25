package EvaRuiz.HealthCarer.medication;

import EvaRuiz.HealthCarer.images.LocalImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;
    @Autowired
    private LocalImageService imageService;

    private Medication checkMedicationExistAndGet(Long id) {
        Optional<Medication> medication = medicationRepository.findById(id);
        if (medication.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found");
        } else {
            return medication.get();
        }
    }

    public List<Medication> findAll() {
        return medicationRepository.findAll();
    }

    public Medication getMedication(Long id) {
        return checkMedicationExistAndGet(id);
    }

    public Medication createMedication(Medication medication) {
        //TODO check preconditions

        return medicationRepository.save(medication);
    }

    public void deleteMedication(Medication medication) throws IOException {
        if (!medication.getBoxImage().isEmpty()) {
            String boxImage = medication.getBoxImage();
            imageService.deleteImage(boxImage, imageService.getImageId(boxImage));
        }
        if (!medication.getPillImage().isEmpty()) {
            String pillImage = medication.getPillImage();
            imageService.deleteImage(pillImage, imageService.getImageId(pillImage));
        }
        medicationRepository.delete(medication);
    }

    public Medication replaceMedication(Medication medication) {
        Medication existingMedication = checkMedicationExistAndGet(medication.getId());
        //TODO check preconditions
        existingMedication.setName(medication.getName());
        existingMedication.setBoxImage(medication.getBoxImage());
        existingMedication.setPillImage(medication.getPillImage());
        existingMedication.setInstructions(medication.getInstructions());
        existingMedication.setDose(medication.getDose());
        existingMedication.setStock(medication.getStock());
        return medicationRepository.save(existingMedication);
    }
}
