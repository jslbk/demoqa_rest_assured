package reqrest.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class HomeworkTest {

    @Test
    @Tag("200")
    void testRegisterSuccessful() {
        RestAssured.baseURI = "https://reqres.in/api";
        String requestBody = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";

        given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .log().uri()

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @Tag("400")
    void testRegisterBadEmail() {
        RestAssured.baseURI = "https://reqres.in/api";
        String requestBody = "{\n" +
                "    \"email\": \"bad.bad@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";

        given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    @Tag("400")
    void testRegisterEmptyEmail() {
        RestAssured.baseURI = "https://reqres.in/api";
        String requestBody = "{\n" +
                "    \"email\": \"\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";

        given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .log().uri()

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    @Tag("400")
    void testRegisterEmptyPassword() {
        RestAssured.baseURI = "https://reqres.in/api";
        String requestBody = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"\"\n" +
                "}";

        given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .log().uri()

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @Tag("404")
    void testSingleUserNotFound() {
        RestAssured.baseURI = "https://reqres.in/api";
        given()
                .log().uri()

                .when()
                .get("/users/23")

                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    @Tag("200")
    void testSingleUserEmail() {
        RestAssured.baseURI = "https://reqres.in/api";
        given()
                .log().uri()

                .when()
                .get("/users/4")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.email", is("eve.holt@reqres.in"));
    }


}