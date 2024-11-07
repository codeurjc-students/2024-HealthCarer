package EvaRuiz.HealthCarer.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private User checkUserExistAndGet(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
    private void checkUser(User user){
        if (user.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }
        if (user.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        if (user.getEncodedPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return checkUserExistAndGet(id);
    }

    public User createUser(User user) {
        checkUser(user);
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        User existingUser = checkUserExistAndGet(user.getId());
        userRepository.delete(existingUser);
    }

    public User updateUser(User user) {
        User existingUser = checkUserExistAndGet(user.getId());
        checkUser(user);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setEncodedPassword(user.getEncodedPassword());
        return userRepository.save(existingUser);
    }
}
