package EvaRuiz.HealthCarer.Restcontroller;

import EvaRuiz.HealthCarer.DTO.UserDTO;
import EvaRuiz.HealthCarer.DTO.UserPassDTO;
import EvaRuiz.HealthCarer.model.LoggedUser;
import EvaRuiz.HealthCarer.model.User;
import EvaRuiz.HealthCarer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	private UserService userService;
	@Autowired
	private LoggedUser loggedUser;

	// Getting a user given its name
	@GetMapping("/{name}")
	public ResponseEntity<UserDTO> getUserByName(@PathVariable String name) {
		loggedUser.setLoggedUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
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
		loggedUser.setLoggedUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());


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
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Edit a user given its name
	@PutMapping("/updateProfile/{name}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable String name, @RequestBody UserPassDTO updatedUser) {
		loggedUser.setLoggedUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		User user = userService.findByUserName(name).orElse(null);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (updatedUser.password() == null || updatedUser.password().isEmpty()) {
			return ResponseEntity.badRequest().body(null);
		}
		if (updatedUser.email() == null || updatedUser.email().isEmpty()) {
			return ResponseEntity.badRequest().body(null);
		}
		if (updatedUser.name() == null || updatedUser.name().isEmpty()) {
			return ResponseEntity.badRequest().body(null);
		}

		user.setName(updatedUser.name());
		user.setEmail(updatedUser.email());
		user.setEncodedPassword(updatedUser.password());
		UserDTO response = new UserDTO(userService.updateUser(user));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Delete a user given its name
	@DeleteMapping("/delete/{name}")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable String name) {
		loggedUser.setLoggedUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		User user = userService.findByUserName(name).orElse(null);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userService.deleteUser(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
