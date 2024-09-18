package EvaRuiz.HealthCarer.med;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/meds")

public class MedRestController {

    @Autowired
    private MedService medService;

    @Autowired
    private ImgService imgService;

    private static final String MEDS_FOLDER = "resources/meds";

    public MedRestController(MedService medService) {
        this.medService = medService;
    }

    @GetMapping("/")
    public List<Med> getMeds() {
        return medService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Med> getMed(@PathVariable Long id) {
        Optional<Med> med = medService.findById(id);
        return med.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Med> createMed(@RequestBody Med med) {
        medService.save(med);

        URI location = fromCurrentRequest().path("/{id}")
                .buildAndExpand(med.getId()).toUri();
        return ResponseEntity.created(location).body(med);
    }
    @PostMapping("/{id}/images")
    public ResponseEntity<Object> uploadImage(@PathVariable long id,
                                              @RequestParam MultipartFile boxImageFile,
                                              @RequestParam MultipartFile pillImageFile) throws IOException {
        Optional<Med> med = medService.findById(id);
        if (med.isPresent()) {
            URI location = fromCurrentRequest().build().toUri();
            Med med1 = med.get();
            med1.setBoxImage(location.toString());
            med1.setPillImage(location.toString());
            medService.save(med1);
            imgService.saveBoxImage(MEDS_FOLDER, med1.getId(), boxImageFile);
            imgService.savePillImage(MEDS_FOLDER, med1.getId(), pillImageFile);
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Med> deleteMed(@PathVariable Long id) {
        Optional<Med> med = medService.findById(id);
        if (med.isPresent()) {
            medService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Med> replaceMed(@PathVariable Long id, @RequestBody Med newMed) {
        Optional<Med> med = medService.findById(id);
        if (med.isPresent()) {
            newMed.setId(id);
            medService.save(newMed);
            return ResponseEntity.ok(newMed);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
