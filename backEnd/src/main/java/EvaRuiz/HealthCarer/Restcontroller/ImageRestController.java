package EvaRuiz.HealthCarer.Restcontroller;

import EvaRuiz.HealthCarer.model.Image;
import EvaRuiz.HealthCarer.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ImageRestController {
    private final ImageService imageService;

//    @GetMapping("/api/image/{id}")
//    public ResponseEntity<Object> getImage(@PathVariable Long id) {
//        Optional<Image> image = this.imageService.findById(id);
//        if (image.isEmpty()) return ResponseEntity.notFound().build();
//        try {
//            return this.imageService.createResponseFromImage(image.get());
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
