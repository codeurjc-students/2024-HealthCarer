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
public class UserRestTests {
    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://localhost:" + port;
    }

    @Test
    @DisplayName("Create a user")
    public void createUser() {
        given().
                contentType("application/json").
                body("{\n" +
                        "\"name\": \"Pepe33\",\n" +
                        "\"email\": \"pepe4@gmail.com\",\n" +
                        "\"password\": \"1234\"\n" +
                        "}").
                when().
                post("/api/users/register").
                then().
                statusCode(201).
                body("name", equalTo("Pepe33")).
                body("email", equalTo("pepe4@gmail.com")).
                body("roles", hasItem("USER"));
    }

    @Test
    @DisplayName("Get a user")
    public void getUser() {
        given().
                auth().basic("user", "pass").
                when().
                get("/api/users/user").
                then().
                statusCode(200).
                body("name", equalTo("user")).
                body("roles", hasItems("USER"));
    }

    @Test
    @DisplayName("Update a user")
    public void updateUser() {
        given().
                auth().basic("admin", "adminpass").
                contentType("application/json").
                body("{\n" +
                        "\"name\": \"Pepe\",\n" +
                        "\"email\": \"pepe@gmail.com\",\n" +
                        "\"password\": \"1234\"\n" +
                        "}").
                when().
                put("/api/users/updateProfile/admin").
                then().
                statusCode(200).body("name", equalTo("Pepe"));
    }

    @Test
    @DisplayName("Delete a user")
    public void deleteUser() {
        given().
                auth().basic("user", "pass").
                when().
                delete("/api/users/delete/user").
                then().
                statusCode(200);
    }
}
