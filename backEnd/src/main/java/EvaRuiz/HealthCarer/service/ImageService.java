package EvaRuiz.HealthCarer.service;

import EvaRuiz.HealthCarer.model.Image;
import EvaRuiz.HealthCarer.model.Medication;
import EvaRuiz.HealthCarer.repository.ImageRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public void saveImage(String id, MultipartFile image) throws IOException {
        Image boxImage = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        boxImage.setBoxImage(image.getBytes());
        boxImage.setContentType(image.getContentType());
        boxImage.setName(image.getOriginalFilename());
        this.imageRepository.saveAndFlush(boxImage);
    }

    public Image save(MultipartFile multipartFile) throws IOException {
            Image image = Image.builder()
                    .id(UUID.randomUUID().toString())
                    .boxImage(multipartFile.getBytes())
                    .contentType(multipartFile.getContentType())
                    .name(multipartFile.getOriginalFilename())
                    .build();
            this.imageRepository.saveAndFlush(image);
        return image;
    }

    public ResponseEntity<Object> createResponseFromImage(String id) {
        Optional<Image> image = imageRepository.findById(id);
        return image.<ResponseEntity<Object>>map(value -> ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, value.getContentType()).body(value.getBoxImage())).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
