package EvaRuiz.HealthCarer.user;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserRestController {
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/me")
	public ResponseEntity<User> me(User user) {
		Optional<User> userLogged = userRepository.findByEmail(user.getEmail());
        return userLogged.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
}
