package EvaRuiz.HealthCarer.user;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/me")
	public ResponseEntity<User> me(HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		if(principal != null) {
			return ResponseEntity.ok(userRepository.findByName(principal.getName()).orElseThrow());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
