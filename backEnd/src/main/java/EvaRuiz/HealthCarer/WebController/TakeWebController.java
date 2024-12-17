package EvaRuiz.HealthCarer.WebController;

import EvaRuiz.HealthCarer.DTO.MedicationDTO;
import EvaRuiz.HealthCarer.DTO.TakeDTO;
import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.model.Take;
import EvaRuiz.HealthCarer.model.User;
import EvaRuiz.HealthCarer.repository.MedicationRepository;
import EvaRuiz.HealthCarer.service.MedicationService;
import EvaRuiz.HealthCarer.service.TakeService;
import EvaRuiz.HealthCarer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/takes")
public class TakeWebController {

    @Autowired
    private TakeService takeService;
    @Autowired
    private UserService userService;
    @Autowired
    private MedicationService medicationService;
    @Autowired
    private MedicationRepository medicationRepository;

    private User addUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userService.findByUserName(username);
        model.addAttribute("logged", true);
        userOptional.ifPresent(user -> model.addAttribute("user", user));
        return userOptional.orElse(null);
    }

    @GetMapping("/")
    public String takes(Model model) {
        User user = addUser(model);
        model.addAttribute("takes", user.getTakes());
        return "/takes/takes";
    }

    @GetMapping("/{id}")
    public String take(Model model, @PathVariable Long id) {
        addUser(model);
        Take take = takeService.getTake(id);
        model.addAttribute("take", take);
        return "/takes/take";
    }


    @GetMapping("/newtake")
    public String newTake(Model model) {
        User user = addUser(model);
        model.addAttribute("medications", user.getMedications());
        model.addAttribute("take", new Take());
        return "/takes/newTakePage";
    }

    @PostMapping("/newtake")
    public String newTake(Model model, @RequestParam String date ,@RequestParam("medications") List<Long> medications) {
        User user = addUser(model);
        Take take = new Take();
        take.setDate(java.sql.Date.valueOf(date));
        take.setUser(user);
        for (Long id : medications) {
            Medication medication = medicationService.getMedicationById(id);
            take.addMedication(medication);
            medication.addTake(take);
        }
        takeService.createTake(take);
        return "redirect:/takes/";
    }
}
