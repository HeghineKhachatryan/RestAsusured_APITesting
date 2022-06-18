package business;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import java.util.concurrent.TimeUnit;

public class Responses {

    public static <T> T getObjectFromResponse(ValidatableResponse response, Class<T> type, String path) {
        return response
                .spec(getResponseSpecification())
                .extract()
                .jsonPath()
                .getObject(path, type);
    }

    public static int getIDFromResponse(ValidatableResponse response, String path) {
        return response
                .spec(getResponseSpecification())
                .extract()
                .jsonPath()
                .getInt(path);
    }

    private static ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectResponseTime(Matchers.lessThan(5L), TimeUnit.SECONDS).build();
    }

    public static void validateResponseAgainstJsonSchema(ValidatableResponse response, String filePath) {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory
                .newBuilder()
                .setValidationConfiguration(ValidationConfiguration
                        .newBuilder()
                        .setDefaultVersion(SchemaVersion.DRAFTV4)
                        .freeze())
                .freeze();

        response
                .assertThat()
                .body(JsonSchemaValidator
                        .matchesJsonSchemaInClasspath(filePath)
                        .using(jsonSchemaFactory));
    }

    public static <T> T getStringFromResponseUnderJsonPath(ValidatableResponse response, String filePath) {
        return response
                .extract()
                .jsonPath()
                .get(filePath);
    }


}
