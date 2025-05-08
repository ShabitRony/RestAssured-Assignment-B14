import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.*;

public class MyRestAssured {
    Properties pro;
    public MyRestAssured() throws IOException {
         pro = new Properties();
        FileInputStream fs = new FileInputStream("./src/test/resources/config.properties");
        pro.load(fs);
    }
    @Test
    public void userRegistration(){
        baseURI =pro.getProperty("baseUrl");
              Response res=  given().contentType("application/json").body("{\n" +
                        "    \"address\": \"20/5 Green Garden 27, North Manikdi, DhakaCantonmen Dhaka-1206\",\n" +
                        "\"email\": \"shabitalahi123+502@gmail.com\",\n" +
                        "\"firstName\": \"Shabit\",\n" +
                        "\"gender\": \"Male\",\n" +
                        "\"lastName\": \"Rony\",\n" +
                        "\"password\": \"1234\",\n" +
                        "\"phoneNumber\": \"+8801704670152\",\n" +
                        "\"termsAccepted\": true\n" +
                        "}")
                        .when().post("./auth/register");
        System.out.println(res.asString());
//                JsonPath jsonObj = new JsonPath();
//                String 
    }
    @Test
    public void doLogin() throws ConfigurationException {
        RestAssured.baseURI=pro.getProperty("baseUrl");
        Response res= given().contentType("application/json")
                .body("{\n" +
                        "    \"email\": \"admin@test.com\",\n" +
                        "    \"password\":\"admin123\"\n" +
                        "}")
                .when().post("/auth/login");

//        System.out.println(res.asString());
        JsonPath jsonObj= res.jsonPath();
        String token = jsonObj.get("token");
        System.out.println(token);
        Utils.setEnvVar("token",token);
    }
    @Test
    public void searchUser() throws ConfigurationException {
        RestAssured.baseURI=pro.getProperty("baseUrl");
        Response res= given().contentType("application/json")
                .header("Authorization","Bearer "+pro.getProperty("token"))
                .when().get("/user/a6ad92f0-1450-4f41-82c4-1363b96c3a2f");
                System.out.println(res.asString());
        JsonPath jsonObj= res.jsonPath();
        String id = jsonObj.get("user._id");
        System.out.println(id);
//        utils.Utils.setEnvVar("userId",id);

    }
//    public void deleteItem(){
//        baseURI = pro.getProperty("baseUrl");
//        Response res = given().contentType("application/json").header("Authorization","Bearer "+pro.getProperty())
//                .when().delete("/costs/")
//    }
}
