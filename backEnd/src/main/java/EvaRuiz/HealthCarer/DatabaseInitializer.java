package EvaRuiz.HealthCarer;

import java.io.IOException;
import java.net.URISyntaxException;


import EvaRuiz.HealthCarer.medication.Medication;
import EvaRuiz.HealthCarer.medication.MedicationRepository;
import EvaRuiz.HealthCarer.user.User;
import EvaRuiz.HealthCarer.user.UserRepository;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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

	@PostConstruct
	public void init() throws IOException, URISyntaxException {

		// Sample medications
		Medication medication = new Medication();
		medication.setName("Paracetamol");
		medication.setStock(10f);
		medication.setDose(1f);
		medication.setInstructions("Tomar con agua");
		medicationRepository.save(medication);
		Medication medication2 = new Medication();
		medication2.setName("Ibuprofeno");
		medication2.setStock(5f);
		medication2.setDose(1f);
		medication2.setInstructions("Tomar con comida");
		medicationRepository.save(medication2);

		// Sample users
		userRepository.save(new User("user", "email", passwordEncoder.encode("pass"), "USER"));
		userRepository.save(new User("admin", "email", passwordEncoder.encode("adminpass"), "USER", "ADMIN"));
	}
}
