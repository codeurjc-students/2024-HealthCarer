package EvaRuiz.HealthCarer.med;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MedService {

    @Autowired
    private MedRepository medRepository;


    public Collection<Med> findAll() {
        return medRepository.findAll();
    }

    public Med findById(Long id) {
        return medRepository.findById(id).orElse(null);
    }

    public void save(Med med) {
        medRepository.save(med);
    }

    public void delete(Med med) {
        medRepository.delete(med);
    }
}
