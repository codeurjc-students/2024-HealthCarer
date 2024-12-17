package EvaRuiz.HealthCarer.service;

import EvaRuiz.HealthCarer.model.User;
import EvaRuiz.HealthCarer.repository.MedicationRepository;
import EvaRuiz.HealthCarer.repository.TakeRepository;
import EvaRuiz.HealthCarer.repository.TreatmentRepository;
import EvaRuiz.HealthCarer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final MedicationRepository medicationRepository;

    private final TreatmentRepository treatmentRepository;

    private final TakeRepository takeRepository;

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
        medicationRepository.deleteAll(existingUser.getMedications());
        treatmentRepository.deleteAll(existingUser.getTreatments());
        takeRepository.deleteAll(existingUser.getTakes());
        userRepository.delete(existingUser);
    }

    public void updateUser(User user) {
        User existingUser = checkUserExistAndGet(user.getId());
        checkUser(user);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setEncodedPassword(user.getEncodedPassword());
        userRepository.save(existingUser);
    }

    public Optional<User> findByUserName(String username) {
        return userRepository.findByName(username);
    }

    public User save(User user) {
        checkUser(user); // Valida que el usuario tenga los campos requeridos
        return userRepository.save(user); // Si es nuevo o existe, lo guarda/actualiza
    }

    public void deleteUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id); // Elimina el usuario de la base de datos por su ID
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);  // Busca un usuario por su email
    }
}
