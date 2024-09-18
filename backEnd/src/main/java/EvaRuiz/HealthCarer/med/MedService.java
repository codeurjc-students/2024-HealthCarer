package EvaRuiz.HealthCarer.med;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedService {

    @Autowired
    private MedRepository medRepository;

    public MedService(MedRepository medRepository) {
        this.medRepository = medRepository;
    }


    public List<Med> findAll() {
        return medRepository.findAll();
    }

    public Optional<Med> findById(Long id) {
        return medRepository.findById(id);
    }

    public void save(Med med) {
        medRepository.save(med);
    }

    public void deleteById(Long id) {
        medRepository.deleteById(id);
    }

}
