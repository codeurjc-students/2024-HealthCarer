package EvaRuiz.HealthCarer.WebController;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class indexWebController {

    @GetMapping("/")
    public String home(Model model) {
        return "login";
    }

}
