package reqrest.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class HomeworkTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    @Tag("200")
    void testRegisterSuccessful() {
        String requestBody = """
                {
                    "email": "eve.holt@reqres.in",
                    "password": "pistol"
                }""";

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
                .body("token", notNullValue());
    }

    @Test
    @Tag("400")
    void testRegisterBadEmail() {
        String requestBody = """
                {
                    "email": "bad.bad@reqres.in",
                    "password": "pistol"
                }""";

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
        String requestBody = """
                {
                    "email": "",
                    "password": "pistol"
                }""";

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
        String requestBody = """
                {
                    "email": "eve.holt@reqres.in",
                    "password": ""
                }""";

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