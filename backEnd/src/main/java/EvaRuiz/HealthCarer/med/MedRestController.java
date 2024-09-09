package EvaRuiz.HealthCarer.med;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

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
        Med med = medService.findById(id);
        if (med == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(med);
        }
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
        Med med = medService.findById(id);
        if (med == null) {
            return ResponseEntity.notFound().build();
        } else {
            medService.delete(med);
            return ResponseEntity.ok(med);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Med> replaceMed(@PathVariable Long id, @RequestBody Med newMed) {
        Med med = medService.findById(id);
        if (med == null) {
            return ResponseEntity.notFound().build();
        } else {
            newMed.setId(id);
            medService.save(newMed);
            return ResponseEntity.ok(newMed);
        }
    }


}
