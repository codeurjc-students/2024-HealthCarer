package EvaRuiz.HealthCarer;


import EvaRuiz.HealthCarer.image.LocalImageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class HealthCarerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthCarerApplication.class, args);
	}

	@Bean
	LocalImageService localImageService() {
		return new LocalImageService();
	}


}
