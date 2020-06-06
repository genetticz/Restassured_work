package base;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.TestUtils;
import utils.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Base {

    private static Logger log = LogManager.getLogger(Base.class.getName());

    /**
    * GetSessionId
    * */

    public static String getSessionId(){
        Response response;

        log.info("Starting Test Case : doLogin");

        String loginPayload = generatePayLoadString("login.json");

        String endPointURI = URL.getEndPoint("/rest/auth/1/session");

        response = POSTRequest(endPointURI, loginPayload);
        log.info("Login Response Body: "+response.getBody().asString());

        String strResponse = TestUtils.getResposeString(response);
        JsonPath jsonRes = TestUtils.jsonParser(strResponse);
        String sessionID = jsonRes.getString("session.value");
        log.info("JIRA JSession ID : " + sessionID);
        return sessionID;
    }

    public static String izaanToken() {
        Response response;
        log.info("Starting Test Case : doLogin");
        String loginPayload = generatePayLoadString("izaanbody.json");
        response = POSTRequest("https://v57pmk39lf.execute-api.us-east-1.amazonaws.com/prod/user-auth", loginPayload);
        log.info("Login Response Body: "+response.getBody().asString());
        String strRes = TestUtils.getResposeString(response);
        JsonPath jsonRes = TestUtils.jsonParser(strRes);
        String eyToken = jsonRes.getString("idToken");
        log.info("Izaan Sesison Toekn : " + eyToken);
        return eyToken;

    }


    /*
    * Payload generator
    *
    * */

    public static String generatePayLoadString(String filename){
        log.info("Inside PayloadConverter function");
        String filePath = System.getProperty("user.dir")+"/payloads/"+filename;
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }
    /*
     * Methods to make http call
     */

    public static Response GETRequest(String uRI) {

        log.info("Inside GETRequest call");
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        Response response = requestSpecification.get(uRI);
        log.debug(requestSpecification.log().all());
        return response;
    }

    public static Response POSTRequest(String uRI, String strJSON) {
        log.info("Inside POSTRequest call");
        RequestSpecification requestSpecification = RestAssured.given().body(strJSON);
        requestSpecification.contentType(ContentType.JSON);
        Response response = requestSpecification.post(uRI);
        log.debug(requestSpecification.log().all());
        return response;
    }

    public static Response POSTRequest(String uRI, String strJSON, String sessionID) {
        log.info("Inside POSTRequest call");
        RequestSpecification requestSpecification = RestAssured.given().body(strJSON);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.header("cookie", "JSESSIONID=" + sessionID+"");
        Response response = requestSpecification.post(uRI);
        log.debug("Post Request Specifications"+requestSpecification.log().all());
        return response;
    }

    public static Response POSTRequestVid(String uRI, String strJSON, String authToken) {
        log.info("Inside POSTRequest call");
        RequestSpecification requestSpecification = RestAssured.given().body(strJSON);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.header("Authorization", authToken);
        Response response = requestSpecification.post(uRI);
        log.debug("Post Request Specifications"+requestSpecification.log().all());
        return response;
    }

    public static Response PUTRequest(String uRI, String strJSON, String sessionID) {
        log.info("Inside PUTRequest call");
        RequestSpecification requestSpecification = RestAssured.given().body(strJSON);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.header("cookie", "JSESSIONID=" + sessionID+"");
        Response response = requestSpecification.put(uRI);
        log.debug("PUT Request Specifications: "+requestSpecification.log().all());
        return response;
    }

    public static Response DELETERequest(String uRI, String strJSON) {
        log.info("Inside DELETERequest call");
        RequestSpecification requestSpecification = RestAssured.given().body(strJSON);
        requestSpecification.contentType(ContentType.JSON);
        Response response = requestSpecification.delete(uRI);
        log.debug(requestSpecification.log().all());
        return response;
    }
}
