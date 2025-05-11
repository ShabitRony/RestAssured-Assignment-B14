package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import setup.ItemModel;
import setup.UserModel;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class BaseController {
    Properties prop;
    public BaseController(Properties prop){
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
    public Response getUserListWithInvalidAuthorization(){
        RestAssured.baseURI=prop.getProperty("baseUrl");
        return  given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("tokennn"))
                .when().get("/user/users");

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
    public Response editUserInfo(String userId, UserModel userModel) {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        ObjectMapper mapper = new ObjectMapper(); // Jackson for JSON conversion

        String requestBody = "";
        try {
            requestBody = mapper.writeValueAsString(userModel); // Convert object to JSON string
        } catch (Exception e) {
            e.printStackTrace();
        }

        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("token"))
                .body(requestBody)
                .when()
                .put("/user/" + userId);

    }
    public Response userLogin(UserModel userModel) {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        return given().contentType("application/json").body(userModel).when().post("/auth/login");
    }

    public Response getItemListWithInvalidAuthorization() {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        return given().contentType("application/json").header("Authorization", "Bearer " + prop.getProperty("admin_token "))
                .when().get("/costs");
    }
    public Response getItemList() {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        return given().contentType("application/json").header("Authorization", "Bearer " + prop.getProperty("user_token"))
                .when().get("/costs");
    }

    public Response addItem(ItemModel itemModel) {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        return given().contentType("application/json").header("Authorization", "Bearer " + prop.getProperty("user_token"))
                .body(itemModel)
                .when().post("/costs");
    }

    public Response editItemInfo(String itemId, ItemModel itemModel) {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        ObjectMapper mapper = new ObjectMapper();

        String requestBody = "";
        try {
            requestBody = mapper.writeValueAsString(itemModel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("user_token"))
                .body(requestBody)
                .when()
                .put("/item/" + itemId);
    }
    public Response deleteItem(String itemId) {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        return given().contentType("application/json").header("Authorization", "Bearer " + prop.getProperty("user_token"))
                .when().delete("/costs/"+itemId);
    }
}
