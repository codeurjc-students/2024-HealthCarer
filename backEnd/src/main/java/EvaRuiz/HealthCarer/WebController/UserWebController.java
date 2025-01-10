package EvaRuiz.HealthCarer.WebController;

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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class UserWebController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String login () {
        return "/users/login";
    }

    @RequestMapping("/loginerror")
    public String loginError() {
        return "/users/loginerror";
    }


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
            model.addAttribute("user", user);
            return "redirect:/profile";
        } else {
            return "redirect:/loginerror";
        }
    }

    @GetMapping("/index")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userService.findByUserName(username);

        model.addAttribute("logged", true);
        model.addAttribute("userName", username);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "index"; // Página de inicio
        }

        return "error"; // Si no se encuentra el usuario, mostramos error

    }

    @GetMapping("/profile")
    public String userProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userService.findByUserName(username);

        model.addAttribute("logged", true);
        model.addAttribute("userName", username);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "/users/profile"; // Página del perfil del usuario
        }

        return "error"; // Si no se encuentra el usuario, mostramos error
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User()); // Formulario de registro
        return "/users/registerform";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email,
                               @RequestParam String password, @RequestParam String confirmPassword,
                               RedirectAttributes redirectAttributes) {

        // Check if the email is already registered
        if (userService.findByEmail(email).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "El correo electrónico ya está registrado.");
            return "/users/regerror";  // Redirect back to the register page
        }

        // Ensure the passwords match
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden.");
            return "/users/regerror";  // Redirect back to the register page
        }

        // Create a new User object and set its properties
        User newUser = new User();
        newUser.setName(username);
        newUser.setEmail(email);

        // Encode the password before saving it
        newUser.setEncodedPassword(passwordEncoder.encode(password));

        // Save the user to the database
        userService.save(newUser);

        // Add a success message and redirect to the login page
        redirectAttributes.addFlashAttribute("success", "¡Registro exitoso! Puedes iniciar sesión.");
        return "redirect:/";  // Redirect to the login page
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session and clear the authentication
        SecurityContextHolder.clearContext();

        // You can invalidate the HTTP session if you're using one
        request.getSession().invalidate();

        // Optionally, remove the CSRF token and session cookies
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Make the cookie expire immediately
        response.addCookie(cookie);


        // Redirect the user to the home page or login page
        return "redirect:/";  // Or you can redirect to the login page "/login"
    }


    @GetMapping("/editProfile")
    public String editProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userService.findByUserName(username);

        model.addAttribute("logged", true);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "/users/editProfilePage"; // Página de edición de perfil
        }

        return "error"; // Si no se encuentra el usuario, mostramos error
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@RequestParam("username") String username,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<User> userOptional = userService.findByUserName(currentUsername);


        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(username);
            user.setEmail(email);
            if (!password.isEmpty() && password.equals(confirmPassword)) {
                user.setEncodedPassword(passwordEncoder.encode(password));
            }
            User user1 = userService.updateUser(user);
            model.addAttribute("user", user1);
            model.addAttribute("updatedprofile", true);
            return "/users/profile";
        }

        return "error";
    }


    @PostMapping("/delete-account")
    public String deleteAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<User> userOptional = userService.findByUserName(currentUsername);

        // Elimina la cuenta del usuario
        userOptional.ifPresent(user -> userService.deleteUserById(user.getId()));

        return "redirect:/logout"; // Logout después de eliminar cuenta
    }


    // Recuperación de contraseña y otros métodos seguirían igual.
}
