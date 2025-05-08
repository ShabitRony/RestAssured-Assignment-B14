package testrunner;

import com.github.javafaker.Faker;
import controller.UserController;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
//import org.junit.jupiter.api.Test;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import setup.Setup;
import setup.UserModel;
import utils.Utils;

import static io.restassured.RestAssured.given;

public class UserTestRunner extends Setup {
    private UserController userController;

    @BeforeClass
    public void initUserController(){
        userController = new UserController(prop);
    }

    @Test(priority = 1,description = "User Registration")
    public void userRegistration() throws ConfigurationException {
        UserModel userModel = new UserModel();
        Faker faker = new Faker();

        String firstName="Shabit";
        String lastName="Rony";
        String email = "shabitalahi123+523@gmail.com";
        String password = "1234";
        String address = "Dhaka";
        String gender ="Male";
        String phoneNumber = "01704670152";
        boolean termsAccepted = true;

        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setAddress(address);
        userModel.setGender(gender);
        userModel.setPhoneNumber(phoneNumber);
        userModel.setTermsAccepted(String.valueOf(termsAccepted));

        Response res = userController.userRegistration(userModel);
        JsonPath jsonObj= res.jsonPath();
        String userId = jsonObj.get("_id");
        System.out.println(userId);
        Utils.setEnvVar("userId",userId);
    }
    @Test(priority = 2,description = "User Login")
    public void userLogin() throws ConfigurationException {
        UserModel userModel = new UserModel();
        userModel.setEmail("shabitalahi123+523@gmail.com");
        userModel.setPassword("1234");
        Response res = userController.userLogin(userModel);
        JsonPath jsonObj= res.jsonPath();
        String user_token = jsonObj.get("token");
        System.out.println(user_token);
        Utils.setEnvVar("user_token",user_token);
    }


}
