package EvaRuiz.HealthCarer.medication;


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

    public MedicationDTO toDTO(Medication medication){
        return new MedicationDTO(medication.getId(), medication.getName(), medication.getBoxImage(), medication.getStock(), medication.getInstructions(), medication.getDose());
    }

    public Medication toEntity(MedicationDTO dto) {
        return new Medication(dto.getName(), dto.getBoxImage(), dto.getStock(), dto.getInstructions(), dto.getDose());
    }

    public void checkMedication(Medication medication) {
        if (medication.getName() == null || medication.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Medication name is required");
        }
        if (medication.getStock() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Medication stock must be positive");
        }
        if (medication.getInstructions() == null || medication.getInstructions().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Medication instructions are required");
        }
        if (medication.getDose() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Medication dose must be positive");
        }
    }

    public void assingMedicationProperties(Medication oldMedication, String name, String boxImage, Float stock, String instructions, Float dose) {
        oldMedication.setName(name);
        oldMedication.setBoxImage(boxImage);
        oldMedication.setStock(stock);
        oldMedication.setInstructions(instructions);
        oldMedication.setDose(dose);
    }

    public List<Medication> findAll() {
        return medicationRepository.findAll();
    }

    public Medication getMedication(Long id) {

        Optional<Medication> medication = medicationRepository.findById(id);
        if (medication.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found");
        } else {
            return medication.get();
        }
    }

    public Medication createMedication(Medication medication) {
        return medicationRepository.save(medication);
    }



    public void deleteMedication(Medication medication) throws IOException {
        medicationRepository.delete(medication);
    }

    public Medication replaceMedication(Medication medication) {
        return medicationRepository.save(medication);
    }


}
