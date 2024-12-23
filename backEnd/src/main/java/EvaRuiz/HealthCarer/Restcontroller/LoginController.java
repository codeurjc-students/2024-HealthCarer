package EvaRuiz.HealthCarer.Restcontroller;

import EvaRuiz.HealthCarer.DTO.UserPassDTO;
import EvaRuiz.HealthCarer.model.User;
import EvaRuiz.HealthCarer.security.jwt.AuthResponse;
import EvaRuiz.HealthCarer.security.jwt.LoginRequest;
import EvaRuiz.HealthCarer.security.jwt.UserLoginService;
import EvaRuiz.HealthCarer.service.UserService;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	// Log in
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserPassDTO user) {

		String username = user.name();
		String password = user.password();
		Optional<User> userFound = userService.findByUserName(username);
		if (userFound.isEmpty() || !passwordEncoder.matches(password, userFound.get().getEncodedPassword())) {
			return ResponseEntity.status(401).body("Credenciales incorrectas.");
		} else {
			return ResponseEntity.ok("Login exitoso.");
		}
	}

	// Log out
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {

		request.getSession().invalidate();
		SecurityContextHolder.clearContext();


		Cookie cookie = new Cookie("JSESSIONID", null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);
		response.addCookie(cookie);


		return ResponseEntity.ok("Logout realizado correctamente.");
	}
}
