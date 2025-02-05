package ServiceTests;

import EvaRuiz.HealthCarer.model.Treatment;
import EvaRuiz.HealthCarer.repository.TreatmentRepository;

import EvaRuiz.HealthCarer.service.TreatmentService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

@DisplayName("Treatment Unitary tests")
public class TreatmentServiceTest {

        private final TreatmentRepository treatmentRepository = mock(TreatmentRepository.class);
        private final TreatmentService treatmentService = new TreatmentService(treatmentRepository);

        @BeforeAll
        public static void setUp() {

        }

        @Test
        @DisplayName("Create new treatment")
        public void createTreatmentTest() {

            // Given
            Treatment treatment1 = new Treatment();
            treatment1.setName("Tratamiento1");
            treatment1.setDispensingFrequency(2);
            treatment1.setStartDate(java.sql.Date.valueOf("2021-01-01"));
            treatment1.setEndDate(java.sql.Date.valueOf("2021-01-31"));

            // When
            when(treatmentRepository.save(treatment1)).thenReturn(treatment1);
            treatmentService.createTreatment(treatment1);

            // Then
            verify(treatmentRepository, times(1)).save(treatment1);
        }

        @Test
        @DisplayName("Update treatment")
        public void updateTreatmentTest() {

            // Given
            Treatment treatment1 = new Treatment();
            Long id = 1L;
            treatment1.setId(id);
            treatment1.setName("Tratamiento1");
            treatment1.setDispensingFrequency(2);
            treatment1.setStartDate(java.sql.Date.valueOf("2021-01-01"));
            treatment1.setEndDate(java.sql.Date.valueOf("2021-01-31"));

            // When
            when(treatmentRepository.save(treatment1)).thenReturn(treatment1);
            treatmentService.createTreatment(treatment1);
            treatment1.setEndDate(java.sql.Date.valueOf("2025-01-01"));
            treatment1.setName("Tratamiento1Largo");
            when(treatmentRepository.findById(id)).thenReturn(java.util.Optional.of(treatment1));
            treatmentService.updateTreatment(id, treatment1);

            // Then
            verify(treatmentRepository, times(2)).save(treatment1);
            verify(treatmentRepository, times(1)).findById(id);
        }

        @Test
        @DisplayName("Delete treatment")
        public void deleteTreatmentTest() {

            // Given
            Treatment treatment1 = new Treatment();
            Long id = 1L;
            treatment1.setId(id);
            treatment1.setName("Tratamiento1");
            treatment1.setDispensingFrequency(2);
            treatment1.setStartDate(java.sql.Date.valueOf("2021-01-01"));
            treatment1.setEndDate(java.sql.Date.valueOf("2021-01-31"));

            // When
            when(treatmentRepository.save(treatment1)).thenReturn(treatment1);
            Treatment createdTreatment = treatmentService.createTreatment(treatment1);
            when(treatmentRepository.findById(id)).thenReturn(java.util.Optional.of(treatment1));
            treatmentService.deleteTreatment(createdTreatment.getId());

            // Then
            verify(treatmentRepository, times(1)).delete(createdTreatment);
        }

        @Test
        @DisplayName("Find treatment by id")
        public void findTreatmentByIdTest() {

            // Given
            Treatment treatment1 = new Treatment();
            Long id = 1L;
            treatment1.setId(id);
            treatment1.setName("Tratamiento1");
            treatment1.setDispensingFrequency(2);
            treatment1.setStartDate(java.sql.Date.valueOf("2021-01-01"));
            treatment1.setEndDate(java.sql.Date.valueOf("2021-01-31"));

            // When
            when(treatmentRepository.save(treatment1)).thenReturn(treatment1);
            treatmentService.createTreatment(treatment1);
            when(treatmentRepository.findById(id)).thenReturn(java.util.Optional.of(treatment1));
            treatmentService.getTreatment(id);

            // Then
            verify(treatmentRepository, times(1)).findById(id);
        }
}
