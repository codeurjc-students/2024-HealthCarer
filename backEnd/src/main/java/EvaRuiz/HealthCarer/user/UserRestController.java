package EvaRuiz.HealthCarer.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

	public interface UserView extends User.BasicAtt {
	}

	@GetMapping("/")
	public List<User> getAllUsers (){
		return userService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
		return ResponseEntity.ok(userMapper.toDTO(userService.getUser(id)));
	}

	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
		User user = userMapper.toDomain(userDTO);
		return ResponseEntity.ok(userMapper.toDTO(userService.createUser(user)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable long id ,@RequestBody UserDTO userDTO) {
		User user = userService.getUser(id);
		User newUser = userMapper.toDomain(userDTO);
		newUser.setId(id);
		return ResponseEntity.ok(userMapper.toDTO(userService.updateUser(newUser)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable long id) {
		User user = userService.getUser(id);
		userService.deleteUser(user);
		return ResponseEntity.ok(userMapper.toDTO(user));
	}
}
