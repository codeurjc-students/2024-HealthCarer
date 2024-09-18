package EvaRuiz.HealthCarer;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static io.restassured.RestAssured.*;
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
				contentType("application/json").
				body("{" +
						"    \"name\": \"Ibuprofeno\",\n" +
						"    \"stock\": 60,\n" +
						"    \"instructions\": \"1 dosis cada 8 horas\",\n" +
						"    \"dose\": 600\n" +
						"}").
				when().
				post("/api/meds/").
				then().statusCode(201).assertThat().
				contentType("application/json").
				body("id", notNullValue()).
				body("name", equalTo("Ibuprofeno")).
				body("stock", equalTo(60F)).
				body("instructions", equalTo("1 dosis cada 8 horas")).
				body("dose", equalTo(600F));

	}

	@Test
	public void medsGetTest() {

		given().
				when().
				get("/api/meds/").
				then().statusCode(200).assertThat().contentType("application/json");

	}

	@Test
	public void medGetByIdTest() {
		Response response = given().
				contentType("application/json").
				body("{" +
						"    \"name\": \"Ibuprofeno\",\n" +
						"    \"stock\": 60,\n" +
						"    \"instructions\": \"1 dosis cada 8 horas\",\n" +
						"    \"dose\": 600\n" +
						"}").
				post("/api/meds/").
				then().statusCode(201).extract().response();

		int id = response.getBody().jsonPath().getInt("id");
		String name = response.jsonPath().getString("name");
		float stock = response.jsonPath().getFloat("stock");
		String instructions = response.jsonPath().getString("instructions");
		float dose = response.jsonPath().getFloat("dose");


		given().
				when().
				get("/api/meds/" + id).
				then().statusCode(200).assertThat().contentType("application/json").
				body("id", equalTo(id)).
				body("name", equalTo(name)).
				body("stock", equalTo(stock)).
				body("instructions", equalTo(instructions)).
				body("dose", equalTo(dose));
	}

	@Test
	public void medDeleteTest() {
		Response response = given().
				contentType("application/json").
				body("{" +
						"    \"name\": \"Ibuprofeno\",\n" +
						"    \"stock\": 60,\n" +
						"    \"instructions\": \"1 dosis cada 8 horas\",\n" +
						"    \"dose\": 600\n" +
						"}").
				post("/api/meds/").
				then().statusCode(201).extract().response();
		long id = response.jsonPath().getLong("id");


		given().
				when().
				delete("/api/meds/" + id).
				then().statusCode(200);

	}
}
