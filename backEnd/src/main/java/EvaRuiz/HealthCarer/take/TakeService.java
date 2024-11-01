package EvaRuiz.HealthCarer.take;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class TakeService {

    @Autowired
    private TakeRepository takeRepository;

    private Take checkTakeExistAndGet(Long id) {
        Optional<Take> take = takeRepository.findById(id);
        if (take.isPresent()) {
            return take.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Take not found");
        }
    }
    public List<Take> findAll() {
        return takeRepository.findAll();
    }

    public Take getTake(Long id) {
        return checkTakeExistAndGet(id);
    }

    public Take createTake(Take take) {
        //TODO check preconditions
        take.setTimestamp(Calendar.getInstance());
        return takeRepository.save(take);
    }

    public void deleteTake(Take take) {
        if(take.getMedications()!=null){
            take.setMedications(null);
        }
        takeRepository.delete(take);
    }

    public Take replaceTake(Take take) {
        Take existingTake = checkTakeExistAndGet(take.getId());
        //TODO check preconditions
        existingTake.setTimestamp(take.getTimestamp());
        return takeRepository.save(existingTake);
    }

}
