package in.reqres;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class HomeWorkTests {

    @Test
    void registerSuccessful () {
        String authData = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

                given()
                .log().all()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void userCreate () {
        String authData = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

                given()
                .log().all()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("morpheus"), "job", is("leader"));
    }

    @Test
    void loginUnsuccessful () {
        String authData = "{ \"email\": \"peter@klaven\"}";
                given()
                        .log().all()
                        .contentType(JSON)
                        .body(authData)
                        .when()
                        .post("https://reqres.in/api/login")
                        .then()
                        .log().all()
                        .statusCode(400)
                        .body("error", is("Missing password"));
    }
}
