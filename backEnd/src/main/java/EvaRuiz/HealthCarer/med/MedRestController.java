package EvaRuiz.HealthCarer.med;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/meds")

public class MedRestController {

    @Autowired
    private MedService medService;

    @GetMapping("/")
    public Collection<Med> getMeds() {
        return medService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Med> getMed(@PathVariable Long id) {
        Optional<Med> med = medService.findById(id);
        return ResponseEntity.of(med);
    }

    @PostMapping("/")
    public ResponseEntity<Med> createMed(@RequestBody Med med) {
        medService.save(med);
        URI location = fromCurrentRequest().path("/{id}")
                .buildAndExpand(med.getId()).toUri();
        return ResponseEntity.created(location).body(med);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Med> deleteMed(@PathVariable Long id) {
        Optional<Med> med = medService.findById(id);
        med.ifPresent(m -> medService.deleteById(id));
        return ResponseEntity.of(med);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Med> replaceMed(@PathVariable Long id, @RequestBody Med newMed) {
        Optional<Med> med = medService.findById(id);
        return ResponseEntity.of(med.map(m -> {
            newMed.setId(m.getId());
            medService.save(newMed);
            return newMed;
        }));
    }

}
