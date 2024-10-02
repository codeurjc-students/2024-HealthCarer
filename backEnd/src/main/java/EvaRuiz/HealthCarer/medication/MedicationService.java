package EvaRuiz.HealthCarer.medication;

import EvaRuiz.HealthCarer.images.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ModelMapper modelMapper;

    public List<Medication> findAll() {
        return medicationRepository.findAll();
    }

    public Medication getMedication(Long id) {
        return checkMedicationExistAndGet(id);
    }

    private Medication checkMedicationExistAndGet(long id) {
        Optional<Medication> medication = medicationRepository.findById(id);
        if (medication.isPresent()) {
            return medication.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found");
        }
    }

    public Medication createMedication(MedicationDTO medicationDTO){

        Medication medication = modelMapper.map(medicationDTO, Medication.class);

        if (medicationDTO.getBoxImage() != null) {
            String boxImage = imageService.createImage(medicationDTO.getBoxImage());
            medication.setBoxImage(boxImage);
        }
        if (medicationDTO.getPillImage() != null) {
            String pillImage = imageService.createImage(medicationDTO.getPillImage());
            medication.setPillImage(pillImage);
        }
        return medicationRepository.save(medication);
    }

    public Medication createMedication(Medication medication) {
        //TODO check preconditions
        return medicationRepository.save(medication);
    }

    public void deleteMedication(Medication medication) {
        medicationRepository.delete(medication);
        if (medication.getBoxImage() != null) {
            imageService.deleteImage(medication.getBoxImage());
        }
        if (medication.getPillImage() != null) {
            imageService.deleteImage(medication.getPillImage());
        }
    }

    public Medication updateMedication(Medication medication) {
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
