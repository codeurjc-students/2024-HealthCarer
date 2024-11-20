package EvaRuiz.HealthCarer.Restcontroller;

import EvaRuiz.HealthCarer.DTO.UserDTO;
import EvaRuiz.HealthCarer.model.User;
import EvaRuiz.HealthCarer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@GetMapping("/{name}")
	public ResponseEntity<?> getUserById(@PathVariable String name) {
		Optional<User> userOptional = userService.findByUserName(name);
		return userOptional
				.map(user -> {
					UserDTO userDTO = new UserDTO(user);
					return ResponseEntity.ok(userDTO);
				})
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@PostMapping("/register")
	public ResponseEntity<UserDTO> register(@RequestBody User user) {
		if (user.getEncodedPassword() == null || user.getEncodedPassword().isEmpty()) {
			return ResponseEntity.badRequest().body(null);
		}
		String encodedPassword = passwordEncoder.encode(user.getEncodedPassword());
		user.setEncodedPassword(encodedPassword);
		userService.save(user);
		UserDTO response = new UserDTO(user);
		return ResponseEntity.status(200).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
		String username = userDTO.getUsername();
		String password = userDTO.getPassword();
		if (username.equals("user") && password.equals("pass")) {
			return ResponseEntity.ok("Inicio de sesión exitoso.");
		} else {
			return ResponseEntity.status(401).body("Credenciales incorrectas.");
		}
	}

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
