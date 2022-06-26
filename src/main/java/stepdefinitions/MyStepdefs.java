package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;

import java.util.List;

public class MyStepdefs {

    ValidatableResponse response;
    StringBuilder body = new StringBuilder();
    String getBodyFromPost;

    @Given("I have run json server")
    public void iHaveRunJsonServer() {
        System.out.println("Json server is running");
    }

    @When("I get all {}")
    public void iGetAllUsers(String endpoint) {
        ValidatableResponse response = getALlPosts();
        response
                .extract()
                .response()
                .prettyPrint();
    }

    @Then("I should see that list of posts is more than {int}")
    public void iShouldSeeThatListOfPostsIsMoreThan(int size) {
        ValidatableResponse response = getALlPosts();
        List<Object> id = response.extract().jsonPath().getList("id");
        String message = "Size of posts is " + id.size() + " and expected size is " + size;
        Assert.assertTrue(id.size() > size, message);
    }

    @And("I select {int} for post id")
    public StringBuilder iSelectForPostId(int id) {
        return body = new StringBuilder(RestAssured
                .given()
                .when()
                .get("posts/" + id)
                .then()
                .extract()
                .body()
                .asString());
    }

    @When("I set parameters for posts")
    public void iSetValuesForPosts(DataTable table) {
        List<String> values = table.values();
        body.append("{");
        for (int i = 0; i < values.size(); i++) {
            if (values.size() - 2 > i) {
                body.append("\"").append(values.get(i++)).append("\":\"").append(values.get(i)).append("\",");
            } else {
                body.append("\"").append(values.get(i++)).append("\":\"").append(values.get(i));
            }
        }
        body.append("\"}");
    }

    @When("I create specs for posts")
    public RequestSpecification iCreateSpecsForPosts() {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setBody(body.toString())
                .build();
    }

    @And("I create a post")
    public void iMakePostRequest() {
        response = RestAssured
                .given()
                .spec(iCreateSpecsForPosts())
                .when()
                .post("posts")
                .then();
    }

    @Then("I should see the post")
    public void iShouldSeeThePost() {
        printResponseBody();
    }

    @When("I delete {int} from server")
    public void iDeleteFromServer(int idNumber) {
        response = RestAssured
                .given()
                .when()
                .delete("posts/" + idNumber)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Then("I should see success message")
    public void iShouldSeeSuccessMessage() {
        Assert.assertEquals(response.extract().statusCode(), HttpStatus.SC_OK);
        System.out.println("Successfully deleted");
    }

    @Then("Post {int} should be updated")
    public void postShouldBeUpdated(int id) {
        response = RestAssured
                .given(iCreateSpecsForPosts())
                .when()
                .put("posts/" + id)
                .then();
    }

    private void printResponseBody() {
        response.extract().response().body().prettyPrint();
    }

    private ValidatableResponse getALlPosts() {
        return response = RestAssured
                .given()
                .when()
                .get("posts")
                .then();
    }

    @When("I set {string} and {string} for {int} post")
    public void iSetAndForPost(String arg0, String arg1, int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(arg0, arg1);

        response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .when()
                .patch("posts/" + id)
                .then();
    }
}
