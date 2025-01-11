package EvaRuiz.HealthCarer.Restcontroller;

import EvaRuiz.HealthCarer.DTO.MedicationDTO;
import EvaRuiz.HealthCarer.DTO.TakeDTO;
import EvaRuiz.HealthCarer.model.LoggedUser;
import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.model.Take;
import EvaRuiz.HealthCarer.service.MedicationService;
import EvaRuiz.HealthCarer.service.TakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/takes")
public class TakeRestController {

    @Autowired
    private TakeService takeService;
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private MedicationService medicationService;

    @GetMapping("/")
    public ResponseEntity<List<TakeDTO>> getTakes() {
        loggedUser.setLoggedUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<TakeDTO> takes = new ArrayList<>();
        for (Take take : loggedUser.getLoggedUser().getTakes()) {
            takes.add(new TakeDTO(take));
        }
        return new ResponseEntity<>(takes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TakeDTO> getOneTake(@PathVariable Long id) {
        loggedUser.setLoggedUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Take take = takeService.getTake(id);
        if (take == null) {return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        if (!take.getUser().equals(loggedUser.getLoggedUser())) {return new ResponseEntity<>(HttpStatus.FORBIDDEN);}
        TakeDTO takeDTO = new TakeDTO(take);
        return ResponseEntity.ok(takeDTO);
    }

    @PostMapping("/")
    public ResponseEntity<TakeDTO> createTake(@RequestBody TakeDTO takeDTO) {
        loggedUser.setLoggedUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Take take = new Take(takeDTO);
        take.setUser(loggedUser.getLoggedUser());
        loggedUser.getLoggedUser().getTakes().add(take);
        for (MedicationDTO medicationDTO : takeDTO.medications()) {
            Medication medication = medicationService.getMedicationById(medicationDTO.id());
            take.getMedications().add(medication);
        }
        take = takeService.createTake(take);
        return new ResponseEntity<>(new TakeDTO(take), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TakeDTO> updateTake(@PathVariable Long id, @RequestBody TakeDTO takeDTO) {
        loggedUser.setLoggedUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Take existingTake = takeService.getTake(id);
        if (existingTake == null) {return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        if (!existingTake.getUser().equals(loggedUser.getLoggedUser())) {return new ResponseEntity<>(HttpStatus.FORBIDDEN);}
        Take take = new Take(takeDTO);
        take = takeService.updateTake(id, take);
        return ResponseEntity.ok(new TakeDTO(take));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTake(@PathVariable Long id) {
        loggedUser.setLoggedUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Take existingTake = takeService.getTake(id);
        if (existingTake == null) {return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        if (!existingTake.getUser().equals(loggedUser.getLoggedUser())) {return new ResponseEntity<>(HttpStatus.FORBIDDEN);}
        takeService.deleteTake(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
