package demoqa.tests;

import models.demoqa.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static api.BooksApi.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static demoqa.tests.TestData.credentials;
import static demoqa.tests.TestData.isbn;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("demoqa_api")
public class BooksTest extends TestBase {
    @Test
    void testAddBookToCollections() {
        LoginResponseModel loginResponse = authApi.login(credentials);

        step("Delete collection of the books in profile via API", () ->
                deleteAllBooks(loginResponse.getToken(), loginResponse.getUserId())
        );
        step("Add new book to the profile via API", () -> {
            IsbnModel newIsbn = new IsbnModel();
            newIsbn.setIsbn(isbn);
            List<IsbnModel> isbnList = new ArrayList<>();
            isbnList.add(newIsbn);
            AddBooksModel addNewBook = new AddBooksModel();
            addNewBook.setUserId(loginResponse.getUserId());
            addNewBook.setCollectionOfIsbns(isbnList);

            addNewBook(loginResponse.getToken(), addNewBook);
        });
        step("Check user is logged in and add new book via UI", () -> {

            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
            getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));

            open("/profile");
            assertEquals(credentials.getUserName(), $("#userName-value").getText());
            assertTrue(Objects.requireNonNull($(".rt-tbody a").getAttribute("href")).contains(isbn));
        });
        step("Delete previously added book via API", () -> {
            DeleteBooksRequestModel deleteBook = new DeleteBooksRequestModel();
            deleteBook.setIsbn(isbn);
            deleteBook.setUserId(loginResponse.getUserId());

            deleteBook(loginResponse.getToken(), deleteBook);
        });
        step("Check book is deleted from the collections via UI", () -> {
            open("/profile");
            assertEquals("No rows found", $(".rt-noData").getText());
        });
    }

}