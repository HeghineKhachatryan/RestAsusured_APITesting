import business.Requests;
import business.Responses;
import io.restassured.response.ValidatableResponse;
import model.User;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Test
public class RestAssuredTests extends BaseTest {

    public void makeGetRequest() {
        ValidatableResponse posts = Requests.getAllPosts("posts");
        posts.extract().response().prettyPrint();
        Assert.assertEquals(posts.extract().statusCode(), HttpStatus.SC_OK);

        List<String> titles = Responses.getStringFromResponseUnderJsonPath(posts, "title");
        Assert.assertTrue(titles.contains("New Java book"));

        Responses.validateResponseAgainstJsonSchema(posts, "schema/getRequestSchema.json");
    }

    public void makeGetByIdRequest() {
        ValidatableResponse response = Requests.getPostById("posts/",6);
        response.extract().response().prettyPrint();
        Assert.assertEquals(response.extract().statusCode(), HttpStatus.SC_OK);
    }

    public void makePostRequest() {
        String generateUsers = User.generateUsersWithTitleAuthorAndContent();
        ValidatableResponse posts = Requests.createNewPost("posts", generateUsers);
        posts.extract().response().prettyPrint();
        Assert.assertEquals(posts.extract().statusCode(), HttpStatus.SC_CREATED);
    }

    public void makeDeleteRequest() {
        ValidatableResponse posts = Requests.deletePostById("posts/2");
        Assert.assertEquals(posts.extract().statusCode(),HttpStatus.SC_OK);
    }

    public void makePutRequest() {
        User userBuilder = new User.UserBuilder()
                .setAuthor("Heghine")
                .setContent("Java Book")
                .setTitle("Java for beginners")
                .buildUser();

        ValidatableResponse post = Requests.updateEntirePost("posts/4",userBuilder.toString());
        Assert.assertEquals(post.extract().statusCode(),HttpStatus.SC_OK);

        int id = Responses.getIDFromResponse(post,"id");
        System.out.println("Id of post is " + id);
    }

    public void makePatchRequest() {
        Map<String, String> updatableContent = new HashMap<>();
        updatableContent.put("title","Java for beginners updated");
        ValidatableResponse response = Requests.updatePostPartially("posts/6",updatableContent);
        response.extract().response().prettyPrint();
    }

    public void getObjectFromResponse() {
        ValidatableResponse postSix = Requests.getPostById("posts/",3);
        User user = Responses.getObjectFromResponse(postSix,User.class,"$");
        System.out.println(user.toString());
    }

    public void getObjectFromResponse1() {
        String update = "{\n" +
                "    \"title\": \"New Java book\",\n" +
                "    \"author\": \"Heghine Khachatryan\",\n" +
                "    \"edition\": \"first edition\",\n" +
                "    \"year\": \"2022\",\n" +
                "    \"content\": \"Java Book for beginners\"\n" +
                "}";

        ValidatableResponse post = Requests.createNewPost("posts",update);
        Object user = Responses.getObjectFromResponse(post,Object.class,"$");
        System.out.println(user.toString());
    }
}
