package controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import setup.ItemModel;
import setup.UserModel;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class UserController {
    Properties prop;
    public UserController(Properties prop){
        this.prop=prop;
    }
    public Response adminLogin(UserModel userModel){
        RestAssured.baseURI = prop.getProperty("baseUrl");
        return given().contentType("application/json").body(userModel).when().post("/auth/login");
    }
    public Response userRegistration(UserModel userModel){
        RestAssured.baseURI=prop.getProperty("baseUrl");
        return given().contentType("application/json").body(userModel)
                .when().post("/auth/register");
    }
    public Response getUserList(){
        RestAssured.baseURI=prop.getProperty("baseUrl");
        return  given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("token"))
                .when().get("/user/users");

    }
    public Response searchUser(String userId){
        RestAssured.baseURI = prop.getProperty("baseUrl");
        return given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("token"))
                .when().get("/user/"+userId);
    }
    public  Response editUserInfo( String userId){
        RestAssured.baseURI = prop.getProperty("baseUrl");
        return given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("token")).body("{\n" +
                        "\n" +
                        "\"address\": \"20/5 Green Garden 27, North Manikdi, DhakaCantonmen Dhaka-1206\",\n" +
                        "\"email\": \"shabitalahi123+516@gmail.com\",\n" +
                        "\"firstName\": \"Shakib\",\n" +
                        "\"gender\": \"Male\",\n" +
                        "\"lastName\": \"Rony\",\n" +
                        "\"phoneNumber\":\"01704670150\",\n" +
                        "\"updatedAt\": \"2025-05-05T19:02:17.000Z\"\n" +
                        "\n" +
                        "}")
                .when().put("/user/"+userId);
    }
    public Response userLogin(UserModel userModel){
        RestAssured.baseURI = prop.getProperty("baseUrl");
        return given().contentType("application/json").body(userModel).when().post("/auth/login");
    }
    public Response getItemList(){
        RestAssured.baseURI = prop.getProperty("baseUrl");
        return given().contentType("application/json").header("Authorization","Bearer "+prop.getProperty("user_token"))
                .when().get("/costs");
    }
    public Response addItem(ItemModel itemModel){
        RestAssured.baseURI = prop.getProperty("baseUrl");
        return given().contentType("application/json").header("Authorization","Bearer "+prop.getProperty("user_token"))
                .body(itemModel)
                .when().post("/costs");
    }
}
