package EvaRuiz.HealthCarer.user;

import java.util.ArrayList;
import java.util.List;

import EvaRuiz.HealthCarer.plan.Plan;
import EvaRuiz.HealthCarer.plan.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private PlanService planService;

	public interface UserView extends User.BasicAtt {
	}

	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers (){
		List<UserDTO> users = new ArrayList<>();
		for (User user : userService.findAll()) {
			users.add(toDTO(user.getId(), user));
		}
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
		User user = userService.getUser(id);
		UserDTO userDTO = toDTO(id, user);
		return ResponseEntity.ok(userDTO);
	}

	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
		User user = new User();
		userService.createUser(toEntity(user, userDTO));
		return ResponseEntity.ok(userDTO);

	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable long id ,@RequestBody UserDTO userDTO) {
		User user = userService.getUser(id);
		userService.updateUser(toEntity(user, userDTO));
		return ResponseEntity.ok(userDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable long id) {
		User user = userService.getUser(id);
		UserDTO userDTO = toDTO(id, user);
		userService.deleteUser(user);
		return ResponseEntity.ok(userDTO);
	}

	private UserDTO toDTO(Long id, User user){
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(user.getName());
		userDTO.setEmail(user.getEmail());
		List<Long> plans = new ArrayList<>();
		for (Plan plan : user.getPlans()) {
			plans.add(plan.getId());
		}
		userDTO.setPlans(plans.toArray(new Long[0]));
		return userDTO;
	}
	private User toEntity(User user, UserDTO userDTO){
		user.setName(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setEncodedPassword(userDTO.getPassword());
		for (Long planId : userDTO.getPlans()) {
			user.addPlan(planService.getPlan(planId));
		}
		return user;
	}
}
