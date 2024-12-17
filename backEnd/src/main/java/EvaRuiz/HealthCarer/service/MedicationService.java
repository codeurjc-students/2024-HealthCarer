package EvaRuiz.HealthCarer.service;



import EvaRuiz.HealthCarer.DTO.MedicationDTO;
import EvaRuiz.HealthCarer.model.*;
import EvaRuiz.HealthCarer.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;

    private final TreatmentService treatmentService;

    private final UserService userService;
    private final ImageService imageService;


    public Medication checkMedicationExists(Long id) {
        Optional<Medication> medication = medicationRepository.findById(id);
        if (medication.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found");
        } else {
            return medication.get();
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

    public void assingMedicationProperties(Medication oldMedication, String name, Float stock, String instructions, Float dose) {
        oldMedication.setName(name);
        oldMedication.setStock(stock);
        oldMedication.setInstructions(instructions);
        oldMedication.setDose(dose);
    }

    public List<Medication> getAllMedications() {
        return medicationRepository.findAll().stream().sorted(Comparator.comparing(Medication::getStock)).collect(Collectors.toList());
    }

    public List<Medication> getMedicationsByUser(String name) {
        Optional<User> user = userService.findByUserName(name);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else {
            return user.get().getMedications().stream().sorted(Comparator.comparing(Medication::getStock)).collect(Collectors.toList());
        }
    }

    public Medication createMedication(Medication medication) {
        checkMedication(medication);
        return medicationRepository.save(medication);
    }

    public Medication getMedicationById(Long id) {
        Optional<Medication> medication = medicationRepository.findById(id);
        if (medication.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found");
        } else {
            return medication.get();
        }
    }


    public Medication updateMedication(Long id, MedicationDTO updatedMedication) {
        Medication existingMedication = checkMedicationExists(id);
        checkMedication(new Medication(updatedMedication));
        existingMedication.setName(updatedMedication.name());
        existingMedication.setStock(updatedMedication.stock());
        existingMedication.setInstructions(updatedMedication.instructions());
        existingMedication.setDose(updatedMedication.dose());
        return medicationRepository.save(existingMedication);
    }


    public void deleteMedication(Long id){
        Medication medication = checkMedicationExists(id);
        medication.setUser(null);
        for (Treatment treatment : new ArrayList<>(medication.getTreatments())) {
            medication.removeTreatment(treatment);
        }
        for (Take take : new ArrayList<>(medication.getTakes())) {
            medication.removeTake(take);
        }
        medicationRepository.delete(medication);
    }

    public Medication setImageAndSave(Medication medication, MultipartFile image) {
        return medicationRepository.save(medication);
    }

}
