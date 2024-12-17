package EvaRuiz.HealthCarer.Restcontroller;

import EvaRuiz.HealthCarer.DTO.TakeDTO;
import EvaRuiz.HealthCarer.model.Take;
import EvaRuiz.HealthCarer.service.TakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/takes")
public class TakeRestController {

    @Autowired
    private TakeService takeService;

    @GetMapping("/")
    public ResponseEntity<List<TakeDTO>> getTakes() {
        List<TakeDTO> takes = new ArrayList<>();
        for (Take take : takeService.findAll()) {
            takes.add(new TakeDTO(take));
        }
        return ResponseEntity.ok(takes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TakeDTO> getOneTake(@PathVariable Long id) {
        Take take = takeService.getTake(id);
        TakeDTO takeDTO = new TakeDTO(take);
        return ResponseEntity.ok(takeDTO);
    }

    @PostMapping("/")
    public ResponseEntity<TakeDTO> createTake(@RequestBody TakeDTO takeDTO) {
        Take take = new Take(takeDTO);
        take = takeService.createTake(take);
        return ResponseEntity.ok(new TakeDTO(take));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TakeDTO> updateTake(@PathVariable Long id, @RequestBody TakeDTO takeDTO) {
        Take take = new Take(takeDTO);
        take = takeService.updateTake(id, new TakeDTO(take));
        return ResponseEntity.ok(new TakeDTO(take));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTake(@PathVariable Long id) {
        takeService.deleteTake(id);
        return ResponseEntity.noContent().build();
    }


}
