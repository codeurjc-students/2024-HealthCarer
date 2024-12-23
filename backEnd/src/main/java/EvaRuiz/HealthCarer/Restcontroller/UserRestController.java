package EvaRuiz.HealthCarer.Restcontroller;

import EvaRuiz.HealthCarer.DTO.UserDTO;
import EvaRuiz.HealthCarer.DTO.UserPassDTO;
import EvaRuiz.HealthCarer.model.LoggedUser;
import EvaRuiz.HealthCarer.model.User;
import EvaRuiz.HealthCarer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	private LoggedUser loggedUser;
	@Autowired
	private UserService userService;

	// Getting a user given its name
	@GetMapping("/{name}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable String name) {
		User user = userService.findByUserName(name).orElse(null);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			UserDTO response = new UserDTO(user);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	// Register a new user
	@PostMapping("/register")
	public ResponseEntity<UserDTO> register(@RequestBody UserPassDTO user) {

		if (user.password() == null || user.password().isEmpty()) {
			return ResponseEntity.badRequest().body(null);
		}
		if (user.name() == null || user.name().isEmpty()) {
			return ResponseEntity.badRequest().body(null);
		}
		if (user.email() == null || user.email().isEmpty()) {
			return ResponseEntity.badRequest().body(null);
		}
		User newUser = new User(user.name(), user.email(), user.password(), "USER");
		UserDTO response = new UserDTO(userService.createUser(newUser));
		return ResponseEntity.status(200).body(response);
	}

	// Edit a user given its name
	@PutMapping("/updateProfile/{name}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable String name, @RequestBody UserPassDTO updatedUser) {
		if (!loggedUser.getLoggedUser().getName().equals(name) || !loggedUser.isAdmin()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = userService.findByUserName(name).orElse(null);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (loggedUser.getLoggedUser().equals(user)) {
			if (updatedUser.password() == null || updatedUser.password().isEmpty()) {
				return ResponseEntity.badRequest().body(null);
			}
			if (updatedUser.name() == null || updatedUser.name().isEmpty()) {
				return ResponseEntity.badRequest().body(null);
			}
			if (updatedUser.email() == null || updatedUser.email().isEmpty()) {
				return ResponseEntity.badRequest().body(null);
			}
			user.setName(updatedUser.name());
			user.setEmail(updatedUser.email());
			user.setEncodedPassword(updatedUser.password());
		}
	}
}
