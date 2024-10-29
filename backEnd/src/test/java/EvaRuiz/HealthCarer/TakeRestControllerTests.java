package EvaRuiz.HealthCarer;

import EvaRuiz.HealthCarer.medication.MedicationService;
import EvaRuiz.HealthCarer.take.Take;
import EvaRuiz.HealthCarer.take.TakeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.restassured.RestAssured;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.given;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TakeRestControllerTests {

    @LocalServerPort
    int port;

    private Take newTake;
    private ObjectWriter ow;
    @Autowired
    private TakeService takeService;
    @Autowired
    private MedicationService medicationService;

    @BeforeEach
    public void setup(){
        RestAssured.port = port;
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        newTake = new Take(List.of(medicationService.findAll().get(0)));
    }

    @AfterEach
    void tearDown() {
        newTake = null;
        ow = null;
    }

    @Test
    @DisplayName("GET /api/takes/")
    public void GetMappingOfAllTakes() throws Exception {

        given()
                .when()
                .get("/api/takes/")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("GET /api/takes/{id}")
    public void GetMappingOfOneTake() throws Exception {

        given().pathParam("id", takeService.findAll().get(0).getId())
                .when()
                .get("/api/takes/{id}")
                .then()
                .statusCode(200);

        given().pathParam("id", -1)
                .when()
                .get("/api/takes/{id}")
                .then()
                .statusCode(404);

    }

    @Test
    @DisplayName("POST /api/takes/")
    public void PostMappingOfTake() throws Exception{
        given().contentType("application/json")
                .body(ow.writeValueAsString(newTake))
                .when()
                .post("/api/takes/")
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("PUT /api/takes/{id}")
    public void PutMappingOfTake() throws Exception {

        given().pathParam("id", takeService.findAll().get(0).getId())
                .contentType("application/json")
                .body(ow.writeValueAsString(newTake))
                .when()
                .put("/api/takes/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("DELETE /api/takes/{id}")
    public void DeleteMappingOfTake() throws Exception {

        given().pathParam("id", 10000)
                .when()
                .delete("/api/takes/{id}")
                .then()
                .statusCode(404);

        given().pathParam("id", takeService.findAll().get(0).getId())
                .when()
                .delete("/api/takes/{id}")
                .then()
                .statusCode(200);

    }

}
