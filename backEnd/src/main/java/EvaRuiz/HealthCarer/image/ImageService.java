package EvaRuiz.HealthCarer.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String createImage(MultipartFile multiPartFile);

    void deleteImage(String image);
}
