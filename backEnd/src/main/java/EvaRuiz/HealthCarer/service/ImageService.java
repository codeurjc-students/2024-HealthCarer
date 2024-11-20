package EvaRuiz.HealthCarer.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String createImage(MultipartFile multiPartFile);

    void deleteImage(String image);
}
