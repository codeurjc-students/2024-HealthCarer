package RestTests;

import EvaRuiz.HealthCarer.HealthCarerApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes= HealthCarerApplication.class)
public class MedicationRestTests {

    @LocalServerPort
    int port;
    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://localhost:" + port;
    }


    @Test
    @DisplayName("Create a medication")
    public void createMedication() {
        given().
                auth().basic("admin", "adminpass").
                contentType("application/json").
                body("{\"name\": \"Ibuprofeno\",\"stock\" : 20.0, \"instructions\": \"Tomar con agua\", \"dose\" : 1.0}").
                when().
                post("/api/medications/").
                then().
                statusCode(201).
                body("name", equalTo("Ibuprofeno")).
                body("instructions", equalTo("Tomar con agua"));
    }

    @Test
    @DisplayName("Set and get medication image")
    public void medicationImage() {
        File file = new File("C:\\Users\\alcor\\Desktop\\2024-HealthCarer\\backEnd\\src\\main\\resources\\static\\images\\healthcarer.png");
        given().
                auth().basic("admin", "adminpass").
                multiPart("image", file).
                pathParam("id", 1).
                when().
                post("/api/medications/{id}/image").
                then().
                statusCode(200);

        given().
                auth().basic("admin", "adminpass").
                pathParam("id", 1).
                when().
                get("/api/medications/{id}/image").
                then().
                statusCode(200);
    }

    @Test
    @DisplayName("Update a medication")
    public void updateMedication() {
        given().
                auth().basic("admin", "adminpass").
                pathParam("id", 1).
                contentType("application/json").
                body("{\"name\": \"Ibuprofeno200\",\"stock\" : 20.0, \"instructions\": \"Tomar con comida\", \"dose\" : 1.0}").
                when().
                put("/api/medications/{id}").
                then().
                statusCode(200).
                body("name", equalTo("Ibuprofeno200")).
                body("instructions", equalTo("Tomar con comida")).
                body("stock", equalTo(20.0f));
    }

    @Test
    @DisplayName("Delete a medication")
    public void deleteMedication() {
        given().
                auth().basic("user", "pass").
                pathParam("id", 2).
                when().
                delete("/api/medications/{id}").
                then().
                statusCode(200);
    }

    @Test
    @DisplayName("Get all medications of a user")
    public void getAllMedications() {
        given().
                auth().basic("admin", "adminpass").
                when().
                get("/api/medications/").
                then().
                statusCode(200);

    }

    @Test
    @DisplayName("Get one medication")
    public void getOneMedication() {

        given().
                auth().basic("admin", "adminpass").
                pathParam("id", 1).
                when().
                get("/api/medications/{id}").
                then().
                statusCode(200).
                body("name", equalTo("Paracetamol"));

    }

}
