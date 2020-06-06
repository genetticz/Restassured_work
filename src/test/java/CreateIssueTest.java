import base.Base;
import base.BaseAssertion;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.URL;

public class CreateIssueTest {
    @Test
    public void createIssue(){

        /**
         *
         * 1. Session id
         * 2. Payload
         * 3. Headers
         * 4. You need to make call--> POST, GET, PUT, DELETE
         * 5. Response --> To String response --> Interact using JsonPath and get the json object value
         * 6. Do assertion of the response --> Status code, response body etc
         *
         * */
        String  sessionId = Base.getSessionId();
        String payload = Base.generatePayLoadString("createIssuePayload.json");
        String url = URL.getEndPoint("/rest/api/2/issue/");
        Response response = Base.POSTRequest(url,payload,sessionId);

        BaseAssertion.verifyStatusCode(response,201);
        //  How to assert using Json path ?
        // BaseAssertion.verifyStatusMessage(response,"HTTP/1.1 201");
    }
}
