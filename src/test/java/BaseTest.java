import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void setupClass() {
        RestAssured.baseURI = "http://localhost:3000/";
    }
}
