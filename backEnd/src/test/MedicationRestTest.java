import EvaRuiz.HealthCarer.model.Image;
import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.repository.MedicationRepository;
import EvaRuiz.HealthCarer.service.ImageService;
import EvaRuiz.HealthCarer.service.MedicationService;
import EvaRuiz.HealthCarer.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


@DisplayName("Medication Unitary tests")
public class MedicationRestTest {



    @BeforeAll
    public static void setUp() {

    }

    @Test
    @DisplayName("Create new medication")
    public void createMedicationTest() {

        // Given

        Medication medication1 = new Medication();
        medication1.setName("Paracetamol");
        medication1.setStock(10f);
        medication1.setDose(1f);
        medication1.setInstructions("Tomar con agua");

        MedicationRepository medicationRepository = mock(MedicationRepository.class);
        ImageService imageService = mock(ImageService.class);
        when(medicationRepository.save(medication1)).thenReturn(medication1);
        MedicationService medicationService = new MedicationService(medicationRepository, imageService);

        // When
        Medication createdMedication = medicationService.createMedication(medication1);

        // Then
        verify(medicationRepository, times(1)).save(medication1);
        assert createdMedication.equals(medication1);

    }
}
