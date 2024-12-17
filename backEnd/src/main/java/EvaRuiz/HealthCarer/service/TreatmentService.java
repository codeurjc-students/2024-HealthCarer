package EvaRuiz.HealthCarer.service;

import EvaRuiz.HealthCarer.DTO.TreatmentDTO;
import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.model.Treatment;
import EvaRuiz.HealthCarer.repository.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TreatmentService {


    private final TreatmentRepository treatmentRepository;

    public Treatment checkTreatmentExists(Long id) {
        Optional<Treatment> treatment = treatmentRepository.findById(id);
        if (treatment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Treatment not found");
        } else {
            return treatment.get();
        }
    }

    public void checkTreatment(Treatment treatment) {
        if (treatment.getName() == null || treatment.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Treatment name is required");
        }
        if (treatment.getStartDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Treatment start date is required");
        }
        if (treatment.getEndDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Treatment end date is required");
        }
        if (treatment.getStartDate().after(treatment.getEndDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Treatment start date must be before end date");
        }
        if (treatment.getDispensingFrequency() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Treatment dispensing frequency must be positive");
        }
    }

    public List<Treatment> findAll() {
        return treatmentRepository.findAll();
    }

    public Treatment getTreatment(Long id) {
        return treatmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treatment not found"));
    }

    public Treatment createTreatment(Treatment treatment) {
        checkTreatment(treatment);
        return treatmentRepository.save(treatment);
    }

    public void deleteTreatment(Long id) {
        Treatment treatment = checkTreatmentExists(id);
        for (Medication medication : treatment.getMedications()) {
            medication.getTreatments().remove(treatment);
        }
        treatment.getUser().getTreatments().remove(treatment);
        treatmentRepository.delete(treatment);
    }

    public Treatment updateTreatment(Long id, TreatmentDTO newTreatment) {
        Treatment oldTreatment = checkTreatmentExists(id);
        checkTreatment(new Treatment(newTreatment));
        oldTreatment.setName(newTreatment.name());
        oldTreatment.setStartDate(newTreatment.startDate());
        oldTreatment.setEndDate(newTreatment.endDate());
        oldTreatment.setDispensingFrequency(newTreatment.dispensingFrequency());
        return treatmentRepository.save(oldTreatment);
    }

}
