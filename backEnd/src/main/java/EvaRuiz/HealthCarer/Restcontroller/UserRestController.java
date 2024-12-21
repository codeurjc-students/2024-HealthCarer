package EvaRuiz.HealthCarer.Restcontroller;

import EvaRuiz.HealthCarer.DTO.UserDTO;
import EvaRuiz.HealthCarer.DTO.UserPassDTO;
import EvaRuiz.HealthCarer.model.User;
import EvaRuiz.HealthCarer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	// Obtener los detalles del usuario autenticado
	@GetMapping("/profile/{name}")
	public ResponseEntity<?> getUserById(@PathVariable String name) {
		// Buscar el usuario en el servicio por ID
		Optional<User> userOptional = userService.findByUserName(name);

		// Manejar el caso de usuario encontrado o no encontrado
		return userOptional
				.map(user -> {
					UserDTO userDTO = new UserDTO(user); // Convertir User a UserDTO
					return ResponseEntity.ok(userDTO);
				})
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	// Registrar un nuevo usuario
	@PostMapping("/register")
	public ResponseEntity<UserDTO> register(@RequestBody UserPassDTO user) {
		// Validar que la contraseña no sea nula o vacía
		if (user.password() == null || user.password().isEmpty()) {
			return ResponseEntity.badRequest().body(null);  // Error si la contraseña está vacía
		}
		// Codificar la contraseña
		String encodedPassword = passwordEncoder.encode(user.password());
		User newUser = new User(user.name(), user.email(), encodedPassword, "USER");

		// Guardar el usuario
		userService.save(newUser);

		// Crear el objeto de respuesta

		UserDTO response = new UserDTO(newUser);

		// Retornar el usuario registrado con éxito
		return ResponseEntity.status(200).body(response);
	}

	// Autenticar a un usuario (simulación del login en una API REST)

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserPassDTO user) {
		// Procesar el usuario recibido
		String username = user.name();
		String password = user.password();

		// Lógica de validación
		Optional<User> userFound = userService.findByUserName(username);
		if (userFound.isEmpty() || !passwordEncoder.matches(password, userFound.get().getEncodedPassword())) {
			return ResponseEntity.status(401).body("Credenciales incorrectas.");
		} else {
			return ResponseEntity.ok("Login exitoso.");
		}
	}

	// Log out (Cerrar sesión)
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
		// Invalidar la sesión y limpiar el contexto de seguridad
		request.getSession().invalidate();
		SecurityContextHolder.clearContext();

		// Eliminar la cookie JSESSIONID (opcional)
		Cookie cookie = new Cookie("JSESSIONID", null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0); // Expira inmediatamente
		response.addCookie(cookie);

		// confirmación del logout
		return ResponseEntity.ok("Logout realizado correctamente.");
	}

	@GetMapping("/")
	public ResponseEntity<?> getAllUsers() {
		return ResponseEntity.ok(userService.findAll());
	}
}
