package EvaRuiz.HealthCarer.repository;

import EvaRuiz.HealthCarer.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {


}
