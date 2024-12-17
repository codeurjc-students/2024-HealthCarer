package EvaRuiz.HealthCarer.service;


import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.model.Take;
import EvaRuiz.HealthCarer.model.Treatment;
import EvaRuiz.HealthCarer.repository.MedicationRepository;
import EvaRuiz.HealthCarer.model.User;
import EvaRuiz.HealthCarer.repository.TakeRepository;
import EvaRuiz.HealthCarer.repository.TreatmentRepository;
import EvaRuiz.HealthCarer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;


@Service
public class DatabaseInitializer {

	@Autowired
	private MedicationRepository medicationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TreatmentRepository treatmentRepository;

	@Autowired
	private TakeRepository takeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private boolean initialized = false;

	@PostConstruct
	public void init() {
		if (initialized) {
			return;
		}
		initialized = true;

		// Limpia la tabla de usuarios
		userRepository.deleteAll();
		// Limpia la tabla de medicaciones
		medicationRepository.deleteAll();
		// Limpia la tabla de tratamientos
		treatmentRepository.deleteAll();
		// Limpia la tabla de tomas
		takeRepository.deleteAll();


		// Sample medications
		Medication medication1 = new Medication();
		medication1.setName("Paracetamol");
		medication1.setStock(10f);
		medication1.setDose(1f);
		medication1.setInstructions("Tomar con agua");


		Medication medication2 = new Medication();
		medication2.setName("Ibuprofeno");
		medication2.setStock(5f);
		medication2.setDose(1f);
		medication2.setInstructions("Tomar con comida");


		// Sample users
		User user1 = new User("user", "user1@example.com", passwordEncoder.encode("pass"), "USER");

		User user2 = new User("admin", "admin@example.com", passwordEncoder.encode("adminpass"), "USER", "ADMIN");


		//Sample treatments
		Treatment treatment1 = new Treatment();
		treatment1.setName("Tratamiento1");
		treatment1.setDispensingFrequency(2);
		treatment1.setStartDate(java.sql.Date.valueOf("2021-01-01"));
		treatment1.setEndDate(java.sql.Date.valueOf("2021-01-31"));


		//Sample takes
		Take take1 = new Take();
		take1.setDate(java.sql.Date.valueOf("2021-01-01"));

		// Relaciones
		medication1.setUser(user2);




		medicationRepository.save(medication1);
		medicationRepository.save(medication2);
		userRepository.save(user1);
		userRepository.save(user2);
		treatmentRepository.save(treatment1);
		takeRepository.save(take1);
	}
}
