package EvaRuiz.HealthCarer;
import EvaRuiz.HealthCarer.medication.Medication;
import EvaRuiz.HealthCarer.medication.MedicationRestController;
import EvaRuiz.HealthCarer.medication.MedicationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MedicationRestControllerTests {

	@Mock
	private MedicationService medicationService;
	private Medication medication;

	@InjectMocks
	private MedicationRestController medicationController;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup(){
		medication = new Medication("Ibu", 20, "AAA", 30);
		mockMvc = MockMvcBuilders.standaloneSetup(medicationController).build();
	}
	@AfterEach
	void tearDown() {
		medication = null;
	}

	@Test
	public void PostMappingOfMedication() throws Exception{
		when(medicationService.createMedication(any(Medication.class))).thenReturn(medication);
		mockMvc.perform(post("/api/medications/no-image")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Ibu\",\"stock\":20,\"instructions\":\"AAA\",\"dose\":30}"))
				.andExpect(status().isCreated());
	}
}
