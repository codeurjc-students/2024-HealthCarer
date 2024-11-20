package EvaRuiz.HealthCarer.service;

import java.io.IOException;
import java.net.URISyntaxException;


import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.repository.MedicationRepository;
import EvaRuiz.HealthCarer.model.User;
import EvaRuiz.HealthCarer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class DatabaseInitializer {

	@Autowired
	private MedicationRepository medicationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private boolean initialized = false;

	@PostConstruct
	public void init() {
		if (initialized) {
			return;
		}
		initialized = true;

		// Clean medication table
		medicationRepository.deleteAll();

		// Sample medications
		Medication medication1 = new Medication();
		medication1.setName("Paracetamol");
		medication1.setStock(10f);
		medication1.setDose(1f);
		medication1.setInstructions("Tomar con agua");
		medicationRepository.save(medication1);

		Medication medication2 = new Medication();
		medication2.setName("Ibuprofeno");
		medication2.setStock(5f);
		medication2.setDose(1f);
		medication2.setInstructions("Tomar con comida");
		medicationRepository.save(medication2);

		// Sample users
		if (!userRepository.existsByEmail("user1@example.com")) {
			userRepository.save(new User("user", "user1@example.com", passwordEncoder.encode("pass"), "USER"));
		}

		if (!userRepository.existsByEmail("admin@example.com")) {
			userRepository.save(new User("admin", "admin@example.com", passwordEncoder.encode("adminpass"), "USER", "ADMIN"));
		}
	}
}
