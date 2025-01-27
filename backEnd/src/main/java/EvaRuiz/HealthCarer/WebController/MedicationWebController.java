package EvaRuiz.HealthCarer.WebController;

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

import java.io.IOException;
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
    public String newMedication(Model model, Medication newMedication, MultipartFile boxImage){
        User user = addUser(model);
        newMedication.setUser(user);
        medicationService.createMedication(newMedication);
        newMedication = medicationService.setImageAndSave(newMedication, boxImage);
        user.getMedications().add(newMedication);
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
        return "medications/editMedicationPage";
    }



    @PostMapping("/editmedication/{id}")
    public String editMedication(Model model, @PathVariable Long id, Medication updatedMedication, MultipartFile boxImage) throws IOException {
        User user = addUser(model);
        Medication medication = medicationService.getMedicationById(id);
        if (!user.getMedications().contains(medication)) {
            return "/error";
        }
        updatedMedication.setUser(user);
        updatedMedication.setImage(this.imageService.save(boxImage));
        medicationService.updateMedication(id, updatedMedication);
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
            model.addAttribute("medications", user.getMedications());
            return "/medications/medications";
        } else {
            return "/error";
        }
    }
}
