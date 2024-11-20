package EvaRuiz.HealthCarer.service;


import EvaRuiz.HealthCarer.DTO.MedicationDTO;
import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    public MedicationDTO toDTO(Medication medication){
        try {
            // Turn the Blob to a byte[]
            byte[] boxImageBytes = null;
            if (medication.getBoxImage() != null) {
                boxImageBytes = medication.getBoxImage().getBytes(1, (int) medication.getBoxImage().length());
            }

            return new MedicationDTO(
                    medication.getId(),
                    medication.getName(),
                    boxImageBytes, // Pass the byte[] to a DTO
                    medication.getStock(),
                    medication.getInstructions(),
                    medication.getDose()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error converting Blob to byte[]", e);
        }
    }

    public Medication toEntity(MedicationDTO dto) {
        try {
            Blob boxImageBlob = (dto.getBoxImage() != null) ? new SerialBlob(dto.getBoxImage()) : null;
            return new Medication(dto.getName(), boxImageBlob, dto.getStock(), dto.getInstructions(), dto.getDose());
        } catch (SQLException e) {
            throw new RuntimeException("Error converting byte[] to Blob", e);
        }
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

    public void assingMedicationProperties(Medication oldMedication, String name, byte[] boxImageBytes, Float stock, String instructions, Float dose) {
        oldMedication.setName(name);

        try {
            if (boxImageBytes != null) {
                Blob boxImageBlob = new SerialBlob(boxImageBytes);
                oldMedication.setBoxImage(boxImageBlob);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error converting byte[] to Blob", e);
        }

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
