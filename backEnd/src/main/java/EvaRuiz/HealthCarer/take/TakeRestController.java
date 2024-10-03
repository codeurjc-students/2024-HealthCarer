package EvaRuiz.HealthCarer.take;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/takes")
public class TakeRestController {

    @Autowired
    private TakeService takeService;

    public interface TakeView extends Take.BasicAtt {
    }

    @GetMapping("/")
    @JsonView(TakeView.class)
    public ResponseEntity<List<Take>> getTakes() {
        return ResponseEntity.ok(takeService.findAll());
    }

    @GetMapping("/{id}")
    @JsonView(TakeView.class)
    public ResponseEntity<Take> getOneTake(@PathVariable long id) {
        return ResponseEntity.ok(takeService.getTake(id));
    }

    @PostMapping("/")
    @JsonView(Take.BasicAtt.class)
    public ResponseEntity<Take> createTake(@RequestBody Take take) {
        Take newTake = takeService.createTake(take);
        return ResponseEntity.ok(newTake);
    }

    @PutMapping("/")
    @JsonView(Take.BasicAtt.class)
    public ResponseEntity<Take> updateTake(@RequestBody Take take) {
        Take updatedTake = takeService.updateTake(take);
        return ResponseEntity.ok(updatedTake);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Take> deleteTake(@PathVariable long id) {
        Take take = takeService.getTake(id);
        takeService.deleteTake(take);
        return ResponseEntity.ok(take);
    }

}
