package EvaRuiz.HealthCarer.WebController;

import EvaRuiz.HealthCarer.DTO.TreatmentDTO;
import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.model.Treatment;
import EvaRuiz.HealthCarer.model.User;
import EvaRuiz.HealthCarer.repository.MedicationRepository;
import EvaRuiz.HealthCarer.repository.UserRepository;
import EvaRuiz.HealthCarer.service.MedicationService;
import EvaRuiz.HealthCarer.service.TreatmentService;
import EvaRuiz.HealthCarer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/treatments")
public class TreatmentWebController {

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private UserService userService;
    @Autowired
    private MedicationService medicationService;
    @Autowired
    private MedicationRepository medicationRepository;
    @Autowired
    private UserRepository userRepository;

    private User addUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userService.findByUserName(username);
        model.addAttribute("logged", true);
        userOptional.ifPresent(user -> model.addAttribute("user", user));
        return userOptional.orElse(null);
    }

    @GetMapping("/")
    public String treatments(Model model) {
        User user = addUser(model);
        model.addAttribute("treatments", user.getTreatments());
        return "/treatments/treatments";
    }

    @GetMapping("/{id}")
    public String treatment(Model model, @PathVariable Long id) {
        User user = addUser(model);
        Treatment treatment = treatmentService.getTreatment(id);
        if (user.getTreatments().contains(treatment)) {
            model.addAttribute("treatment", treatment);
            return "/treatments/treatment";
        } else {
            return "/error";
        }
    }

    @GetMapping("/newtreatment")
    public String newTreatment(Model model) {
        User user = addUser(model);
        model.addAttribute("treatment", new Treatment());
        model.addAttribute("medications", user.getMedications());
        return "/treatments/newTreatmentPage";
    }

    @PostMapping("/newtreatment")
    public String newTreatment(Model model, @RequestParam String name, @RequestParam String startDate, @RequestParam String endDate, @RequestParam int dispensingFrequency, @RequestParam(name = "medication") List<Long> medications) throws ParseException {
        User user = addUser(model);
        Treatment treatment = new Treatment();
        treatment.setName(name);
        Date start = java.sql.Date.valueOf(startDate);
        Date end = java.sql.Date.valueOf(endDate);
        treatment.setStartDate(start);
        treatment.setEndDate(end);
        treatment.setDispensingFrequency(dispensingFrequency);
        treatment.setUser(user);
        List<Medication> meds = new ArrayList<>();
        for (Long id : medications) {
            meds.add(medicationService.getMedicationById(id));
        }
        treatment.setMedications(meds);
        user.getTreatments().add(treatment);
        userRepository.save(user);
        Treatment newTreatment = treatmentService.createTreatment(treatment);
        model.addAttribute("treatment", newTreatment);
        return "/treatments/treatment";
    }

    @GetMapping("/edittreatment/{id}")
    public String editTreatment(Model model, @PathVariable Long id) {
        addUser(model);
        Treatment treatment = treatmentService.getTreatment(id);
        model.addAttribute("treatment", treatment);
        return "/treatments/editTreatmentPage";
    }

    @PostMapping("/edittreatment/{id}")
    public String editTreatment(Model model, @PathVariable Long id, @RequestParam String name, @RequestParam Date startDate, @RequestParam Date endDate, @RequestParam int dispensingFrequency) {
        addUser(model);
        Treatment treatment = treatmentService.getTreatment(id);
        treatment.setName(name);
        treatment.setStartDate(startDate);
        treatment.setEndDate(endDate);
        treatment.setDispensingFrequency(dispensingFrequency);
        Treatment newTreatment = treatmentService.updateTreatment(id, new TreatmentDTO(treatment));
        model.addAttribute("treatment", newTreatment);
        return "/treatments/treatment";
    }

    @GetMapping("/removetreatment/{id}")
    public String removeTreatment(Model model, @PathVariable Long id) {
        User user = addUser(model);
        treatmentService.deleteTreatment(id);
        return "/treatments/treatments";
    }
}
