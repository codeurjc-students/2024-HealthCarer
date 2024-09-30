package EvaRuiz.HealthCarer;
import EvaRuiz.HealthCarer.medication.Medication;
import EvaRuiz.HealthCarer.medication.MedicationRestController;
import EvaRuiz.HealthCarer.medication.MedicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MedicationRestControllerTests {

	@Mock
	private MedicationService medicationService;
	private Medication medication;
	private List<Medication> medicationList;
	private ObjectWriter ow;

	@InjectMocks
	private MedicationRestController medicationController;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup(){
		medication = new Medication("Ibuprofeno", 20, "Tomar con agua", 30);
		medicationList = List.of(medication);
		mockMvc = MockMvcBuilders.standaloneSetup(medicationController).build();
		ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	}
	@AfterEach
	void tearDown() {
		medication = null;
		medicationList = null;
	}

	@Test
	public void PostMappingOfMedication() throws Exception{
		when(medicationService.createMedication(any(Medication.class))).thenReturn(medication);
		mockMvc.perform(post("/api/medications/no-image")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ow.writeValueAsString(medication)))
				.andExpect(status().isCreated());
		verify(medicationService, times(1)).createMedication(any(Medication.class));
	}

	@Test
	public void GetMappingOfAllMedications() throws Exception {
		when(medicationService.findAll()).thenReturn(medicationList);
		mockMvc.perform(get("/api/medications/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ow.writeValueAsString(medicationList)))
				.andExpect(status().isOk());
		verify(medicationService, times(1)).findAll();

	}

	@Test
	public void GetMappingOfOneMedication() throws Exception {
		when(medicationService.getMedication(1L)).thenReturn(medication);
		mockMvc.perform(get("/api/medications/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ow.writeValueAsString(medication)))
				.andExpect(status().isOk());
		verify(medicationService, times(1)).getMedication(1L);
	}

	@Test
	public void DeleteMappingOfMedication() throws Exception {
		when(medicationService.getMedication(1L)).thenReturn(medication);
		mockMvc.perform(delete("/api/medications/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ow.writeValueAsString(medication)))
				.andExpect(status().isOk());
		verify(medicationService, times(1)).deleteMedication(medication);
	}

}
