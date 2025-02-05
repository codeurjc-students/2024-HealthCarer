package EvaRuiz.HealthCarer.WebController;

import EvaRuiz.HealthCarer.DTO.TakeDTO;
import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.model.Take;
import EvaRuiz.HealthCarer.model.User;
import EvaRuiz.HealthCarer.service.MedicationService;
import EvaRuiz.HealthCarer.service.TakeService;
import EvaRuiz.HealthCarer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/takes")
public class TakeWebController {

    @Autowired
    private TakeService takeService;
    @Autowired
    private UserService userService;
    @Autowired
    private MedicationService medicationService;

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
    public String newTake(Model model, @RequestParam String date, String dateTime ,@RequestParam("medications") List<Long> medications) {
        User user = addUser(model);
        Take take = new Take();
        LocalDateTime localDateTime = LocalDateTime.parse(date + "T" + dateTime);
        take.setLocalDate(localDateTime);
        take.setUser(user);
        for (Long id : medications) {
            Medication medication = medicationService.getMedicationById(id);
            take.getMedications().add(medication);
        }
        take = takeService.createTake(take);
        model.addAttribute("take", take);
        return "/takes/take";
    }

    @GetMapping("/edittake/{id}")
    public String editTake(Model model, @PathVariable Long id) {
        User user = addUser(model);
        Take take = takeService.getTake(id);
        List<Medication> medicationsList = user.getMedications();
        medicationsList.removeAll(take.getMedications());
        model.addAttribute("medications", medicationsList);
        model.addAttribute("take", take);
        return "/takes/editTakePage";
    }

    @PostMapping("/edittake/{id}")
    public String editTake(Model model, @PathVariable Long id, String date , String dateTime , @RequestParam("medications") List<Long> medications) {
        addUser(model);
        Take take = takeService.getTake(id);
        if (!date.isEmpty() && !dateTime.isEmpty()) {
            LocalDateTime newDate = LocalDateTime.parse(date + "T" + dateTime);
            take.setLocalDate(newDate);
        }
        take.getMedications().clear();
        for (Long idMed : medications) {
            Medication medication = medicationService.getMedicationById(idMed);
            take.getMedications().add(medication);
        }
        take = takeService.updateTake(id, take);
        model.addAttribute("take", take);
        return "/takes/take";
    }

    @GetMapping("/removetake/{id}")
    public String deleteTake(Model model, @PathVariable Long id) {
        User user = addUser(model);
        Take take = takeService.getTake(id);
        if (user.getTakes().contains(take)) {
            user.getTakes().remove(take);
            take.setUser(null);
            takeService.deleteTake(id);
            model.addAttribute("takes", user.getTakes());
            return "/takes/takes";
        } else {
            return "/error";
        }
    }
}
