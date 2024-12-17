package EvaRuiz.HealthCarer.service;

import EvaRuiz.HealthCarer.model.Image;
import EvaRuiz.HealthCarer.repository.ImageRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;


    public Image save(Image image) {
        return imageRepository.save(image);
    }

    public void save(MultipartFile multipartFile) throws IOException {
            Image image = Image.builder()
                    .id(UUID.randomUUID().toString())
                    .boxImage(multipartFile.getBytes())
                    .contentType(multipartFile.getContentType())
                    .name(multipartFile.getOriginalFilename())
                    .build();
            this.imageRepository.saveAndFlush(image);
    }


    public ResponseEntity<Object> createResponseFromImage(Image image) {
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, image.getContentType()).body(image.getBoxImage());
    }


}
