package EvaRuiz.HealthCarer.Restcontroller;

import EvaRuiz.HealthCarer.DTO.UserPassDTO;
import EvaRuiz.HealthCarer.security.jwt.AuthResponse;
import EvaRuiz.HealthCarer.security.jwt.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/auth")
public class LoginController {

	@Autowired
	private UserLoginService userLoginService;


	// Log in
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody UserPassDTO userPassDTO) {

		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userPassDTO.name(),
				userPassDTO.password()
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return ResponseEntity.ok(new AuthResponse(AuthResponse.Status.SUCCESS, "Logged in"));
	}

	// Log out
	@PostMapping("/logout")
	public ResponseEntity<AuthResponse> logOut(HttpServletRequest request, HttpServletResponse response) {
		SecurityContextHolder.clearContext();
		return ResponseEntity.ok(new AuthResponse(AuthResponse.Status.SUCCESS, userLoginService.logout(request, response)));
	}
}
