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
public class TakeRestTests {
    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://localhost:" + port;
    }

    @Test
    @DisplayName("Create a take")
    public void createTake() {
        given().
                auth().basic("admin", "adminpass").
                contentType("application/json").
                body("{\n" +
                        "    \"date\": \"2009-02-20\",\n" +
                        "    \"medications\": [\n" +
                        "    {\n" +
                        "        \"id\": 1\n" +
                        "    }\n" +
                        "]\n" +
                        "}").
                when().
                post("/api/takes/").
                then().
                statusCode(201).
                body("date", equalTo("2009-02-20T00:00:00.000+00:00")).
                body("medications[0].name", equalTo("Paracetamol"));
    }
}
