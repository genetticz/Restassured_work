import base.Base;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

public class VideoLinks {

    @Test
    public void retrieveLinks(){

        String eyToken = Base.izaanToken();
        String payload = Base.generatePayLoadString("getlinks.json");
        Response response = Base.POSTRequestVid("https://j92mpbou24.execute-api.us-east-1.amazonaws.com/class-video/get-by-course-n-filetype", payload,eyToken);
        String strRes = TestUtils.getResposeString(response);
        JsonPath jsonRes = TestUtils.jsonParser(strRes);
        String actual = jsonRes.getString("message");
        Assert.assertEquals("success",actual); //expected -> actuals
    }
}
