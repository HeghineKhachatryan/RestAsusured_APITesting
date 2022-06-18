package business;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.util.Map;

public final class Requests {

    private Requests() {

    }

    public static ValidatableResponse getAllPosts(String endpoint) {
        return RestAssured
                .given()
                .when()
                .get(endpoint)
                .then()
                .log()
                .all();
    }

    public static ValidatableResponse getPostById(String endpoint, int id) {
        return RestAssured
                .given()
                .when()
                .get(endpoint + id)
                .then();
    }

    public static ValidatableResponse createNewPost(String endpoint, Object body) {
        return RestAssured
                .given()
                .spec(getRequestSpecification(body))
                .when()
                .post(endpoint)
                .then()
                .log()
                .all();
    }

    public static ValidatableResponse updateEntirePost(String endpoint, String body) {
        return RestAssured
                .given()
                .spec(getRequestSpecification(body))
                .when()
                .put(endpoint)
                .then()
                .log()
                .all();
    }

    public static ValidatableResponse updatePostPartially(String endpoint, Map<String, String> post) {
        JSONObject jsonObject = new JSONObject(post);
        return RestAssured
                .given()
                .spec(getRequestSpecification(jsonObject.toJSONString()))
                .when()
                .patch(endpoint)
                .then()
                .log()
                .all();
    }

    public static ValidatableResponse deletePostById(String endpoint) {
        return RestAssured
                .given()
                .when()
                .delete(endpoint)
                .then()
                .log()
                .all();
    }

    private static RequestSpecification getRequestSpecification(Object body) {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        return builder
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setBody(body)
                .build();
    }
}
