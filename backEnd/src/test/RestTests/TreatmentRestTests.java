package RestTests;


import EvaRuiz.HealthCarer.HealthCarerApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes= HealthCarerApplication.class)
public class TreatmentRestTests {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://localhost:" + port;
    }

    @Test
    @DisplayName("Create a treatment")
    public void createTreatment() {
        given().
                auth().basic("admin", "adminpass").
                contentType("application/json").
                body("""
                        {
                            "name": "Tratamiento1",
                            "startDate": "2024-10-22T07:13:54.853+00:00",
                            "endDate": "2024-10-29T08:13:54.853+00:00",
                            "dispensingFrequency": 3000,
                            "medications":[
                            {
                                "id": 1
                            }
                        ]
                           \s
                        }""").
                when().
                post("/api/treatments/").
                then().
                statusCode(201).
                body("name", equalTo("Tratamiento1")).
                body("medications[0].name", equalTo("Paracetamol"));
    }

    @Test
    @DisplayName("Update a treatment")
    public void updateTreatment() {
        given().
                auth().basic("admin", "adminpass").
                contentType("application/json").
                body("""
                        {
                            "name": "Plan 7",
                            "startDate": "2024-10-22T07:13:54.853+00:00",
                            "endDate": "2024-10-22T07:13:54.853+00:00",
                            "dispensingFrequency": 9000
                        }""").
                when().
                put("/api/treatments/1").
                then().
                statusCode(200).
                body("name", equalTo("Plan 7")).
                body("dispensingFrequency", equalTo(9000)).
                body("medications", hasSize(1));

    }

    @Test
    @DisplayName("Delete a treatment")
    public void deleteTreatment() {
        given().
                auth().basic("admin", "adminpass").
                when().
                delete("/api/treatments/2").
                then().
                statusCode(200);
    }

    @Test
    @DisplayName("Get all treatments")
    public void getTreatments() {
        given().
                auth().basic("admin", "adminpass").
                when().
                get("/api/treatments/").
                then().
                statusCode(200);
    }

    @Test
    @DisplayName("Get one treatment")
    public void getOneTreatment() {
        given().
                auth().basic("admin", "adminpass").
                when().
                get("/api/treatments/1").
                then().
                statusCode(200).
                body("name", equalTo("Tratamiento1")).
                body("medications", hasSize(1));
    }
}
