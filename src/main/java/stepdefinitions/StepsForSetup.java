package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;

import java.io.IOException;

public class StepsForSetup {

    @BeforeAll
    public static void setup() {
        try {
            Runtime.getRuntime().exec("json-server --watch db.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public static void baseURI() {
        RestAssured.baseURI = "http://localhost:3000/";
    }
}
