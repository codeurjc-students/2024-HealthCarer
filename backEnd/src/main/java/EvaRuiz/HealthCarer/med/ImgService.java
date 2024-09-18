package EvaRuiz.HealthCarer.med;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImgService {

    @Autowired
    private MedRepository medRepository;

    public void saveBoxImage(String folder, Long id, MultipartFile imageFile) {
        medRepository.findById(id).ifPresent(med -> {
            String fileName = imageFile.getOriginalFilename();
            String path = folder + "/" + id + "/" + fileName;
            med.setBoxImage(path);
            medRepository.save(med);
        });
    }
    public void savePillImage(String folder, Long id, MultipartFile imageFile) {
        medRepository.findById(id).ifPresent(med -> {
            String fileName = imageFile.getOriginalFilename();
            String path = folder + "/" + id + "/" + fileName;
            med.setPillImage(path);
            medRepository.save(med);
        });
    }
}
