package EvaRuiz.HealthCarer;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthCarerApplicationTests {

	@LocalServerPort
	int port;
	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	}


	@Test
	public void medAddTest() {
		given().
				body("{\"name\": \"Ibuprofeno\", \"stock\": 5, \"instructions\": \"Tomar con agua\", \"dose\": 1}").
				contentType("application/json").
				when().
				post("/api/meds/").
				then().statusCode(201).assertThat().contentType("application/json").
				body("name", equalTo("Ibuprofeno")).
				body("stock", equalTo(5.0F)).
				body("instructions", equalTo("Tomar con agua")).
				body("dose", equalTo(1.0F));
	}
		;
}
