package ServiceTests;

import EvaRuiz.HealthCarer.model.Take;
import EvaRuiz.HealthCarer.repository.TakeRepository;
import EvaRuiz.HealthCarer.service.TakeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

@DisplayName("Take Unitary tests")
public class TakeServiceTest {

    private final TakeRepository takeRepository = mock(TakeRepository.class);
    private final TakeService takeService = new TakeService(takeRepository);

    @BeforeAll
    public static void setUp() {

    }

    @Test
    @DisplayName("Create new take")
    public void createTakeTest() {

        // Given
        Take take1 = new Take();
        take1.setDate(java.sql.Date.valueOf("2021-01-01"));


        // When
        when(takeRepository.save(take1)).thenReturn(take1);
        takeService.createTake(take1);

        // Then
        verify(takeRepository, times(1)).save(take1);
    }

    @Test
    @DisplayName("Update take")
    public void updateTakeTest() {

        // Given
        Take take1 = new Take();
        Long id = 1L;
        take1.setId(id);
        take1.setDate(java.sql.Date.valueOf("2021-01-01"));


        // When
        when(takeRepository.save(take1)).thenReturn(take1);
        takeService.createTake(take1);
        take1.setDate(java.sql.Date.valueOf("2025-01-01"));
        when(takeRepository.findById(id)).thenReturn(java.util.Optional.of(take1));
        takeService.updateTake(id, take1);

        // Then
        verify(takeRepository, times(2)).save(take1);
        verify(takeRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Delete take")
    public void deleteTakeTest() {

        // Given
        Take take1 = new Take();
        Long id = 1L;
        take1.setId(id);
        take1.setDate(java.sql.Date.valueOf("2021-01-01"));

        // When
        when(takeRepository.save(take1)).thenReturn(take1);
        takeService.createTake(take1);
        when(takeRepository.findById(id)).thenReturn(java.util.Optional.of(take1));
        takeService.deleteTake(id);

        // Then
        verify(takeRepository, times(1)).deleteById(id);
        verify(takeRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Find take by id")
    public void getTakeTest() {

        // Given
        Take take1 = new Take();
        Long id = 1L;
        take1.setId(id);
        take1.setDate(java.sql.Date.valueOf("2021-01-01"));

        // When
        when(takeRepository.save(take1)).thenReturn(take1);
        takeService.createTake(take1);
        when(takeRepository.findById(id)).thenReturn(java.util.Optional.of(take1));
        takeService.getTake(id);

        // Then
        verify(takeRepository, times(1)).findById(id);
    }

}
