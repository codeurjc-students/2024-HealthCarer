package EvaRuiz.HealthCarer.WebController;

import EvaRuiz.HealthCarer.DTO.MedicationDTO;
import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.model.User;
import EvaRuiz.HealthCarer.service.ImageService;
import EvaRuiz.HealthCarer.service.MedicationService;
import EvaRuiz.HealthCarer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequestMapping("/medications")
public class MedicationWebController {

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;


    private User addUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userService.findByUserName(username);
        model.addAttribute("logged", userOptional.isPresent());
        userOptional.ifPresent(user -> model.addAttribute("user", user));
        return userOptional.orElse(null);
    }

    @GetMapping("/")
    public String medications(Model model) {
        User user = addUser(model);
        model.addAttribute("medications", user.getMedications());
        return "/medications/medications";
    }

    @GetMapping("/{id}")
    public String medication(Model model, @PathVariable Long id) {
        User user = addUser(model);
        Medication medication = medicationService.getMedicationById(id);
        if (user.getMedications().contains(medication)) {
            model.addAttribute("medication", medication);
            return "/medications/medication";
        } else {
            return "/error";
        }
    }

    @GetMapping("/newmedication")
    public String newMedication(Model model) {
        addUser(model);
        model.addAttribute("medication", new Medication());
        return "/medications/newMedicationPage";
    }

    @PostMapping("/newmedication")
    public String newMedication(Model model, @RequestParam String name, @RequestParam Float stock, @RequestParam String instructions, @RequestParam Float dose, @RequestParam MultipartFile boxImage) {
        User user = addUser(model);
        Medication medication = new Medication();
        medication.setName(name);
        medication.setStock(stock);
        medication.setInstructions(instructions);
        medication.setDose(dose);
        user.getMedications().add(medication);
        userService.save(user);
        medication.setUser(user);
        Medication newMedication = medicationService.createMedication(medication);
        model.addAttribute("medication", newMedication);
        return "/medications/medication";
    }

    @GetMapping("/editmedication/{id}")
    public String editMedication(Model model, @PathVariable Long id) {
        User user = addUser(model);
        Medication medication = medicationService.getMedicationById(id);
        if (!user.getMedications().contains(medication)) {
            return "/error";
        }
        model.addAttribute("medication", medication);
        return "/medications/editMedicationPage";
    }

    @PostMapping("/editmedication/{id}")
    public String editMedication(Model model, @PathVariable Long id, @RequestParam String name, @RequestParam Float stock, @RequestParam String instructions, @RequestParam Float dose) {
        User user = addUser(model);
        Medication medication = medicationService.getMedicationById(id);
        if (!user.getMedications().contains(medication)) {
            return "/error";
        }
        medication.setName(name);
        medication.setStock(stock);
        medication.setInstructions(instructions);
        medication.setDose(dose);
        medicationService.createMedication(medication);
        model.addAttribute("medication", medication);
        return "/medications/medication";
    }

    @GetMapping("/removemedication/{id}")
    public String deleteMedication(Model model, @PathVariable Long id) {
        User user = addUser(model);
        Medication medication = medicationService.checkMedicationExists(id);
        if (user.getMedications().contains(medication)) {
            user.getMedications().remove(medication);
            medication.setUser(null);
            medicationService.deleteMedication(id);
            userService.save(user);
            model.addAttribute("medications", user.getMedications());
            return "/medications/medications";
        } else {
            return "/error";
        }
    }
}
