package EvaRuiz.HealthCarer.med;

import EvaRuiz.HealthCarer.images.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public Medication getMed(Long id) {
        return checkMedExistAndGet(id);
    }

    public void deleteMed(Medication medication) {
        medicationRepository.delete(medication);
        if (medication.getBoxImage() != null) {
            imageService.deleteImage(medication.getBoxImage());
        }
        if (medication.getPillImage() != null) {
            imageService.deleteImage(medication.getPillImage());
        }
    }

    public Medication updateMed(Medication medication) {
        Medication oldMedication = checkMedExistAndGet(medication.getId());
        oldMedication.setName(medication.getName());
        oldMedication.setBoxImage(medication.getBoxImage());
        oldMedication.setInstructions(medication.getInstructions());
        oldMedication.setDose(medication.getDose());
        return this.medicationRepository.save(oldMedication);
    }

    private Medication checkMedExistAndGet(long id) {
        Optional<Medication> med = medicationRepository.findById(id);
        if (med.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found");
        }
        return med.get();
    }

    public Medication createMed(MedicationDTO medicationDTO) {
        Medication medication = modelMapper.map(medicationDTO, Medication.class);
        if (medicationDTO.getBoxImage() != null) {
            String image = imageService.createImage(medicationDTO.getBoxImage());
            medication.setBoxImage(image);
        }
        if (medicationDTO.getPillImage() != null) {
            String image = imageService.createImage(medicationDTO.getPillImage());
            medication.setPillImage(image);
        }
        return medicationRepository.save(medication);
    }

    public Medication createMed(Medication medication) {
        return medicationRepository.save(medication);
    }
}
