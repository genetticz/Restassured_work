import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class SimplaeAPITest {


    public  String simpleTest() throws IOException {
        String url = "http://localhost:8080/rest/auth/1/session";
        String loginPayload = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") +"/payloads/login.json")));

        RequestSpecification requestSpecification = RestAssured.given().body(loginPayload);
        requestSpecification.contentType(ContentType.JSON);

        Response response = requestSpecification.post(url);
        String stringResponse = response.asString();
        JsonPath jsonPath = new JsonPath(stringResponse);
        String sessionId = jsonPath.get("session.value");
        System.out.println(sessionId);

        return sessionId;

    }

    @Test
    public void createIssue() throws IOException {

        String url = "http://localhost:8080/rest/api/2/issue/" ;
        String createIssuePayload = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") +"/payloads/createIssuePayload.json")));

        RequestSpecification requestSpecification = RestAssured.given().log().all().body(createIssuePayload);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.header("Cookie", "JSESSIONID=" + simpleTest());

        Response response = requestSpecification.post(url);

        String stringResponse = response.asString();
        JsonPath jsonPath = new JsonPath(stringResponse);
        String ticketKey = jsonPath.get("key");

        Assert.assertEquals(response.statusCode(), 201);
        //Assert.assertEquals(ticketKey, "ABCD-14");

      //  System.out.println(response.asString());

    }
}
