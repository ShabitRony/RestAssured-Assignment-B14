package testrunner;

import com.github.javafaker.Faker;
import controller.BaseController;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
//import org.junit.jupiter.api.Test;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import setup.Setup;
import setup.UserModel;
import utils.Utils;

import static io.restassured.RestAssured.given;

public class UserTestRunner extends Setup {
    private BaseController baseController;

    @BeforeClass
    public void initUserController(){
        baseController = new BaseController(prop);
    }
    @Test(priority = 1, description = "User Registration with Existing Mail")
    public void userRegistrationWithExistingEmail() throws ConfigurationException {
        UserModel userModel = new UserModel();
        String firstName = "Shabit";
        String lastName = "Rony";
        String email = "shabitalahi123+550@gmail.com";
        String password = "1234";
        String address = "Dhaka";
        String gender = "Male";
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

        Response res = baseController.userRegistration(userModel);
        System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();
        String actualMessage = jsonPath.get("message");
        String expectedMessgae = "User already exists with this email address";
        Assert.assertTrue(actualMessage.contains(expectedMessgae));

    }
    @Test(priority = 2,description = "User Registration")
    public void userRegistration() throws ConfigurationException {
        UserModel userModel = new UserModel();
        String firstName="Shabit";
        String lastName="Rony";
        String email = "shabitalahi123+555@gmail.com";
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

        Response res = baseController.userRegistration(userModel);
        JsonPath jsonObj= res.jsonPath();
        String userId = jsonObj.get("_id");
        System.out.println(userId);
        Utils.setEnvVar("userId",userId);
    }
    @Test(priority = 3,description = "User Login With Wrong Creds")
    public void userLoginWithWrongCreds() throws ConfigurationException {
        UserModel userModel = new UserModel();
        userModel.setEmail("shabitalahi123+523@gmail.com");
        userModel.setPassword("12345678");
        Response res = baseController.userLogin(userModel);
        System.out.println(res.asString());
        JsonPath jsonObj= res.jsonPath();
        String actualMessage = jsonObj.get("message");
        String expectedMessage ="Invalid email or password";
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test(priority = 4,description = "User Login")
    public void userLogin() throws ConfigurationException {
        UserModel userModel = new UserModel();
        userModel.setEmail("shabitalahi123+523@gmail.com");
        userModel.setPassword("1234");
        Response res = baseController.userLogin(userModel);
        JsonPath jsonObj= res.jsonPath();
        String user_token = jsonObj.get("token");
        System.out.println(user_token);
        Utils.setEnvVar("user_token",user_token);
    }


}
