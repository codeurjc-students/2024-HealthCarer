package EvaRuiz.HealthCarer.repository;

import EvaRuiz.HealthCarer.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}
