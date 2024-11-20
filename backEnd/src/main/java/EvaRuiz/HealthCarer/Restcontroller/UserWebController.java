package EvaRuiz.HealthCarer.Restcontroller;

import EvaRuiz.HealthCarer.service.UserService;
import EvaRuiz.HealthCarer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class UserWebController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String login () {
        return "login";

    @RequestMapping("/loginerror")
    public String loginError() {
        return "loginerror";




    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.findByUserName(username).orElse(null);
        if (user != null && passwordEncoder.matches(password, user.getEncodedPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    password
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            redirectAttributes.addFlashAttribute("logged", true);
            redirectAttributes.addFlashAttribute("userName", username);
            return "redirect:/index";
        } else {
            return "redirect:/loginerror";
        }
    }



    @GetMapping("/index")
    public String userProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userService.findByUserName(username);
        model.addAttribute("logged", true);
        model.addAttribute("userName", username);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "index";
        }
        return "error";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "/registerform";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email,
                               @RequestParam String password, @RequestParam String confirmPassword,
                               Model model, RedirectAttributes redirectAttributes) {
        if (userService.findByUserName(username).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "El nombre de usuario ya está registrado.");
            return "/regerror";
        }
        if (userService.findByEmail(email).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "El correo electrónico ya está registrado.");
            return "/regerror";
        }
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden.");
            return "/regerror";
        }
        User newUser = new User();
        newUser.setName(username);
        newUser.setEmail(email);
        newUser.setEncodedPassword(passwordEncoder.encode(password));
        userService.save(newUser);
        redirectAttributes.addFlashAttribute("success", "¡Registro exitoso! Puedes iniciar sesión.");
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/login";
    }


/*
    @PostMapping("/updateProfile")
    public String updateProfile(User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<User> userOptional = userService.findByUserName(currentUsername);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setName(updatedUser.getName());
            userService.save(user); // Guarda los cambios en el perfil
            return "redirect:/profile"; // Redirigir al perfil después de actualizar
        }

        return "error"; // Si no se encuentra el usuario
    }


    @PostMapping("/delete-account")
    public String deleteAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<User> userOptional = userService.findByUserName(currentUsername);

        // Elimina la cuenta del usuario
        userOptional.ifPresent(user -> userService.deleteUserById(user.getId()));

        return "redirect:/logout"; // Logout después de eliminar cuenta
    }*/


    // Recuperación de contraseña y otros métodos seguirían igual.
}
