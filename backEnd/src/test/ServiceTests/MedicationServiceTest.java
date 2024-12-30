package ServiceTests;

import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.repository.MedicationRepository;
import EvaRuiz.HealthCarer.service.ImageService;
import EvaRuiz.HealthCarer.service.MedicationService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


@DisplayName("Medication Unitary tests")
public class MedicationServiceTest {

    private final MedicationRepository medicationRepository = mock(MedicationRepository.class);
    private final ImageService imageService = mock(ImageService.class);
    private final MedicationService medicationService = new MedicationService(medicationRepository, imageService);

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

        // When
        when(medicationRepository.save(medication1)).thenReturn(medication1);
        medicationService.createMedication(medication1);

        // Then
        verify(medicationRepository, times(1)).save(medication1);
    }

    @Test
    @DisplayName("Update medication")
    public void updateMedicationTest() {

        // Given
        Medication medication1 = new Medication();
        Long id = 1L;
        medication1.setId(id);
        medication1.setName("Paracetamol");
        medication1.setStock(10f);
        medication1.setDose(1f);
        medication1.setInstructions("Tomar con agua");



        // When
        when(medicationRepository.save(medication1)).thenReturn(medication1);
        Medication createdMedication = medicationService.createMedication(medication1);
        medication1.setStock(5f);
        medication1.setDose(2f);
        medication1.setInstructions("Tomar con comida");
        when(medicationRepository.findById(id)).thenReturn(java.util.Optional.of(medication1));
        medicationService.updateMedication(id, medication1);

        // Then
        verify(medicationRepository, times(2)).save(medication1);
        verify(medicationRepository, times(1)).findById(id);

    }

    @Test
    @DisplayName("Delete medication")
    public void deleteMedicationTest() {

        // Given
        Medication medication1 = new Medication();
        Long id = 1L;
        medication1.setId(id);
        medication1.setName("Paracetamol");
        medication1.setStock(10f);
        medication1.setDose(1f);
        medication1.setInstructions("Tomar con agua");

        // When
        when(medicationRepository.save(medication1)).thenReturn(medication1);
        medicationService.createMedication(medication1);
        when(medicationRepository.findById(id)).thenReturn(java.util.Optional.of(medication1));
        medicationService.deleteMedication(id);

        // Then
        verify(medicationRepository, times(1)).delete(medication1);

    }

    @Test
    @DisplayName("Get a medication by id")
    public void getMedicationByIdTest() {

        // Given
        Medication medication1 = new Medication();
        Long id = 1L;
        medication1.setId(id);
        medication1.setName("Paracetamol");
        medication1.setStock(10f);
        medication1.setDose(1f);
        medication1.setInstructions("Tomar con agua");

        // When
        when(medicationRepository.save(medication1)).thenReturn(medication1);
        medicationService.createMedication(medication1);
        when(medicationRepository.findById(id)).thenReturn(java.util.Optional.of(medication1));
        medicationService.getMedicationById(id);

        // Then
        verify(medicationRepository, times(1)).findById(id);

    }

}
