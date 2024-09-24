package EvaRuiz.HealthCarer.medication;

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

        //TODO: check if images are null

        return medicationRepository.save(medication);
    }

    public Medication createMedication(Medication medication) {
        return medicationRepository.save(medication);
    }

    public void deleteMedication(Medication medication) {
        medicationRepository.delete(medication);
        //TODO: delete images
    }

    public Medication updateMedication(Medication medication) {
        Medication oldMedication = checkMedicationExistAndGet(medication.getId());

        //TODO check preconditions

        oldMedication.setName(medication.getName());
        oldMedication.setBoxImage(medication.getBoxImage());
        oldMedication.setPillImage(medication.getPillImage());
        oldMedication.setInstructions(medication.getInstructions());
        oldMedication.setDose(medication.getDose());
        oldMedication.setStock(medication.getStock());

        return medicationRepository.save(medication);
    }
}
