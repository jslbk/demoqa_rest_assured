package api;

import models.AddBooksModel;
import models.DeleteBooksRequestModel;

import static io.restassured.RestAssured.given;
import static specifications.ApiSpec.*;

public class BooksApi {
    public static void deleteAllBooks(String token, String userId) {
        given(basicRequestSpec)
                .header("Authorization", "Bearer " + token)
                .queryParam("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .log().all()
                .spec(responseSpec204);
    }

    public static void deleteBook(String token, DeleteBooksRequestModel deleteBook) {
        given(basicRequestSpec)
                .header("Authorization", "Bearer " + token)
                .body(deleteBook)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .log().all()
                .spec(responseSpec204);
    }

    public static void addNewBook(String token, AddBooksModel addNewBook) {
        given(basicRequestSpec)
                .header("Authorization", "Bearer " + token)
                .body(addNewBook)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec201);
        }

}
