package EvaRuiz.HealthCarer.service;

import EvaRuiz.HealthCarer.DTO.TakeDTO;
import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.model.Take;
import EvaRuiz.HealthCarer.repository.TakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TakeService {

    private final TakeRepository takeRepository;

    public Take checkTakeExists(Long id) {
        return takeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Take not found"));
    }

    public void checkTake(Take take) {
        if (take.getDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Take date is required");
        }
    }

    public List<Take> findAll() {
        return takeRepository.findAll();
    }

    public Take getTake(Long id) {
        return takeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Take not found"));
    }

    public Take createTake(Take take) {
        checkTake(take);
        return takeRepository.save(take);
    }

    public void deleteTake(Long id) {
        Take take = checkTakeExists(id);
        for (Medication medication : take.getMedications()) {
            medication.getTakes().remove(take);
        }
        take.getUser().getTakes().remove(take);
        takeRepository.deleteById(id);
    }

    public Take updateTake(Long id, TakeDTO newTake) {
        Take existingTake = checkTakeExists(id);
        Take take = new Take(newTake);
        checkTake(take);
        existingTake.setDate(newTake.date());
        return takeRepository.save(existingTake);
    }


}
