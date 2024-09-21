package EvaRuiz.HealthCarer.images;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public String createImage(MultipartFile multiPartFile);

    public void deleteImage(String image);
}
