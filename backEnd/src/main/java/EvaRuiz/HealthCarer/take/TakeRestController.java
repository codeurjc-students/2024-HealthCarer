package EvaRuiz.HealthCarer.take;
import EvaRuiz.HealthCarer.medication.Medication;
import EvaRuiz.HealthCarer.medication.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/takes")
public class TakeRestController {

    @Autowired
    private TakeService takeService;
    @Autowired
    private TakeMapper takeMapper;
    @Autowired
    private MedicationService medicationService;

    public interface TakeView extends Take.BasicAtt {
    }

    @PostConstruct
    public void init() {
        Medication m1 = new Medication("Medication1",1, "Description1", 1);
        Medication m2 = new Medication("Medication2",2, "Description2", 2);
        medicationService.createMedication(m1);
        medicationService.createMedication(m2);
        List<Medication> medications = List.of(m1,m2);
        Take take = new Take(medications);
        takeService.createTake(take);
    }

    @GetMapping("/")
    public Collection<TakeDTO> getTakes() {
        return takeMapper.toDTOs(takeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TakeDTO> getOneTake(@PathVariable long id) {
        Take take = takeService.getTake(id);
        if (take != null) {
            return ResponseEntity.ok(takeMapper.toDTO(take));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<TakeDTO> createTake(@RequestBody TakeDTO takeDTO) {
        Take take = takeMapper.toDomain(takeDTO);
        return ResponseEntity.created(URI.create("/" + take.getId())).body(takeMapper.toDTO(takeService.createTake(take)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<TakeDTO> updateTake(@PathVariable long id, @RequestBody TakeDTO newTakeDTO) {
        Take take = takeService.getTake(id);
        Take newTake = takeMapper.toDomain(newTakeDTO);
        newTake.setId(take.getId());
        newTake.setTimestamp(take.getTimestamp());
        newTake = takeService.replaceTake(newTake);
        return ResponseEntity.ok(takeMapper.toDTO(newTake));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TakeDTO> deleteTake(@PathVariable long id) {
        Take take = takeService.getTake(id);
        takeService.deleteTake(take);
        return ResponseEntity.ok(takeMapper.toDTO(take));
    }

}
