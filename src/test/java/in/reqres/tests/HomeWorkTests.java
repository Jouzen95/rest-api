package in.reqres.tests;

import in.reqres.models.RegisterBodyLombokModel;
import in.reqres.models.RegisterBodyPojoModel;
import in.reqres.models.RegisterResponseLombokModel;
import in.reqres.models.RegisterResponsePojoModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeWorkTests {

    @Test
    void registerSuccessful () {
        RegisterBodyPojoModel authData = new RegisterBodyPojoModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");

        RegisterResponsePojoModel registerResponse = given()
                .log().all()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(RegisterResponsePojoModel.class);

        assertEquals(4, registerResponse.getId());
        assertEquals("QpwL5tke4Pnpja7X4", registerResponse.getToken());


    }
    @Test
    void registerLombokSuccessful () {
        RegisterBodyLombokModel authData = new RegisterBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");

        RegisterResponseLombokModel registerResponse = given()
                .log().all()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(RegisterResponseLombokModel.class);

        assertEquals(4, registerResponse.getId());
        assertEquals("QpwL5tke4Pnpja7X4", registerResponse.getToken());


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
    @Test
    void loginSuccessful () {
        String authData = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";
        given()
                .log().all()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
    @Test
    void loginMissingEmail () {
        String authData = "{ \"email\": \"\", \"password\": \"cityslicka\"}";
        given()
                .log().all()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }
}
