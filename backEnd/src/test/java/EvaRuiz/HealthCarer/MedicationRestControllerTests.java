package EvaRuiz.HealthCarer;


import EvaRuiz.HealthCarer.medication.Medication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;



import java.io.File;






@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MedicationRestControllerTests {


    @LocalServerPort
    int port;

	private ObjectWriter ow;
	private Medication medication;
	private File boxImage;
	private File pillImage;

    @BeforeEach
	public void setup(){
		RestAssured.port = port;
		ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		medication = new Medication("Xumadull", 90, "Tomar solo", 3);
		boxImage = new File("files/Ibuprofeno.jpg");
		pillImage = new File("files/Ibuprofeno2.jpg");
    }

	@AfterEach
	void tearDown() {
		medication = null;
		boxImage = null;
		pillImage = null;
		ow = null;
	}

	@Test
	@DisplayName("GET /api/medications/")
	public void GetMappingOfAllMedications() throws Exception {

		given()
				.when()
				.get("/api/medications/")
				.then()
				.statusCode(200)
				.body("size()", Matchers.equalTo(2));
	}

	@Test
	@DisplayName("GET /api/medications/{id}")
	public void GetMappingOfOneMedication() throws Exception {

		given().pathParam("id", 1)
				.when()
				.get("/api/medications/{id}")
				.then()
				.statusCode(200)
				.body("name", Matchers.equalTo("Ibuprofeno"));

		given().pathParam("id", 5)
				.when()
				.get("/api/plans/{id}")
				.then()
				.statusCode(404);

	}

	@Test
	@DisplayName("POST /api/medications/no-image")
	public void PostMappingOfMedication() throws Exception{

		given().contentType("application/json")
				.body(ow.writeValueAsString(medication))
				.when()
				.post("/api/medications/")
				.then()
				.statusCode(201)
				.body("name", Matchers.equalTo("Xumadull"))
				.body("dose", Matchers.equalTo(3F));
	}



	@Test
	@DisplayName("POST /api/medications/{id}/images")
	public void PostMappingOfMedication2() throws Exception{

		given().pathParam("id", 1)
				.multiPart("boxImage", boxImage)
				.multiPart("pillImage", pillImage)
				.when()
				.post("/api/medications/{id}/images")
				.then()
				.statusCode(201);
	}


	@Test
	@DisplayName("PUT /api/medications/{id}")
	public void PutMappingOfMedication() throws Exception {

		given().pathParam("id", 2)
				.contentType("application/json")
				.body(ow.writeValueAsString(medication))
				.when()
				.put("/api/medications/{id}")
				.then()
				.statusCode(200);
	}

	@Test
	@DisplayName("DELETE /api/medications/{id}")
	public void DeleteMappingOfMedication() throws Exception {

		given().pathParam("id", 5)
				.when()
				.delete("/api/medications/{id}")
				.then()
				.statusCode(404);
		
		given().pathParam("id", 2)
				.when()
				.delete("/api/medications/{id}")
				.then()
				.statusCode(200);

		//TODO El test falla cuando se intenta borrar un medicamento con imagenes asociadas
	}
}
