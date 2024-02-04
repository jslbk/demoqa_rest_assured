package demoqa.tests;

import models.demoqa.CredentialsModel;
import org.junit.jupiter.api.Tag;

@Tag("demoqa_api")
public class TestData {

    public static String login = "jtest",
    password = "jTest-1234%",
    isbn = "9781449331818";
    public static CredentialsModel credentials = new CredentialsModel(login, password);

}
