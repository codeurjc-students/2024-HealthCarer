package EvaRuiz.HealthCarer;

import EvaRuiz.HealthCarer.plan.Plan;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.restassured.RestAssured;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Calendar;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlanRestControllerTests {

    @LocalServerPort
    int port;

    private Plan plan;
    private ObjectWriter ow;

    @BeforeEach
    public void setup(){
        RestAssured.port = port;
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        plan = new Plan("PlanTest", Calendar.getInstance(), Calendar.getInstance(),90);
    }

    @AfterEach
    void tearDown() {
        plan = null;
        ow = null;
    }

    @Test
    @DisplayName("GET /api/plans/")
    public void GetMappingOfAllPlans() throws Exception {

        given()
                .when()
                .get("/api/plans/")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1));
    }

    @Test
    @DisplayName("GET /api/plans/{id}")
    public void GetMappingOfOnePlan() throws Exception {

        given().pathParam("id", 3)
                .when()
                .get("/api/plans/{id}")
                .then()
                .statusCode(200)
                .body("name", Matchers.equalTo("PlanTest"));

        given().pathParam("id", 5)
                .when()
                .get("/api/plans/{id}")
                .then()
                .statusCode(404);

    }

    @Test
    @DisplayName("POST /api/plans/")
    public void PostMappingOfPlan() throws Exception{

        given().contentType("application/json")
                .body(ow.writeValueAsString(plan))
                .when()
                .post("/api/plans/")
                .then()
                .statusCode(201)
                .body("name", Matchers.equalTo("PlanTest"))
                .body("distance", Matchers.equalTo(90));
    }

    @Test
    @DisplayName("PUT /api/plans/{id}")
    public void PutMappingOfPlan() throws Exception {

        given().pathParam("id", 3)
                .contentType("application/json")
                .body(ow.writeValueAsString(plan))
                .when()
                .put("/api/plans/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("DELETE /api/plans/{id}")
    public void DeleteMappingOfPlan() throws Exception {

        given().pathParam("id", 8)
                .when()
                .delete("/api/plans/{id}")
                .then()
                .statusCode(404);

        given().pathParam("id", 3)
                .when()
                .delete("/api/plans/{id}")
                .then()
                .statusCode(200);

    }
}
