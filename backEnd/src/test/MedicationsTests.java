import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.repository.MedicationRepository;
import EvaRuiz.HealthCarer.service.MedicationService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


@DisplayName("MedicationService Unitary tests")
public class MedicationsTests {

    private final MedicationRepository medicationRepository = mock(MedicationRepository.class);
    //private final MedicationService medicationService = new MedicationService(medicationRepository);
    private Medication medication;

    @BeforeAll
    public static void setUp() {
        Medication medication = new Medication("FAKE MEDICATION", 10, "FAKE INSTRUCTIONS", 5.0f);
    }


    @Test
    @DisplayName("Cuando se guarda una medicación utilizando MedicationService, se guarda en el repositorio")
    public void createMedication() {
        // Given
        when(medicationRepository.save(medication)).thenReturn(medication);

        // When
        //medicationService.createMedication(medication);

        // Then
        verify(medicationRepository, times(1)).save(medication);
    }

    @Test
    @DisplayName("Cuando se borra una medicación utilizando MedicationService, se borra del repositorio")
    public void deleteMedication() {
        // Given
        when(medicationRepository.save(medication)).thenReturn(medication);
        when(medicationRepository.existsById(medication.getId())).thenReturn(true);

        // When
        //medicationService.deleteMedication(medication.getId());

        // Then
        verify(medicationRepository, times(1)).delete(medication);
    }

    @Test
    @DisplayName("Cuando se actualiza una medicación utilizando MedicationService, se actualiza en el repositorio")
    public void updateMedication() {
        // Given
        when(medicationRepository.save(medication)).thenReturn(medication);
        when(medicationRepository.existsById(medication.getId())).thenReturn(true);

        // When
        //medicationService.updateMedication(medication);

        // Then
        verify(medicationRepository, times(1)).save(medication);
    }


}
