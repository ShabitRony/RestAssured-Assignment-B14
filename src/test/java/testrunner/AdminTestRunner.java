package testrunner;

import controller.BaseController;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import setup.Setup;
import setup.UserModel;
import utils.Utils;

public class AdminTestRunner extends Setup {
    private BaseController baseController;

    @BeforeClass
    public void initUserController(){
        baseController = new BaseController(prop);
    }
    @Test(priority = 1,description = "Admin Login with Wrong Creds")
    public void adminLoginWithwrongCreds() throws ConfigurationException {
        UserModel userModel = new UserModel();
        userModel.setEmail("admin@test.com");
        userModel.setPassword("admin1234");
        Response res = baseController.adminLogin(userModel);
        System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();
        String actualMessage = jsonPath.get("message");
        String expectedMessage ="Invalid email or password";
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test(priority = 2, description = "Admin Login")
    public void adminLogin() throws ConfigurationException {
        UserModel userModel = new UserModel();
        userModel.setEmail("admin@test.com");
        userModel.setPassword("admin123");
        Response res = baseController.adminLogin(userModel);
        JsonPath jsonObj= res.jsonPath();
        String token = jsonObj.get("token");
        System.out.println(token);
        Utils.setEnvVar("token",token);
    }
    @Test(priority = 3,description = "Get User List with Invalid Authorization")
    public void getUserListWithInvalidAuthorization() throws ConfigurationException {
        Response res =  baseController.getUserListWithInvalidAuthorization();
        System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();
        String actualMessage = jsonPath.get("message");
        String expectedMessage = "Not authorized, token failed";
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test(priority = 4,description = "Get User List")
    public void getUserList() throws ConfigurationException {
        Response res =  baseController.getUserList();
        System.out.println(res.asString() );
    }
    @Test(priority = 5,description = "Search User By Wrong Id")
    public void searchUserByWrongId() throws ConfigurationException {
        Response res = baseController.searchUser(prop.getProperty("444555"));
        System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();
        String actualMessage = jsonPath.get("message");
        String expectedMessage = "User not found";
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test(priority = 6,description = "Search User By Id")
    public void searchUser() throws ConfigurationException {
        Response res =  baseController.searchUser(prop.getProperty("userId"));
        System.out.println(res.asString() );

    }
    @Test(priority = 7,description = "Edit User Info")
    public void editUserInfo(){
        UserModel user = new UserModel();
        user.setEmail("shabitalahi123+555@gmail.com");
        user.setFirstName("Shakib");
        user.setLastName("Rony");
        user.setAddress("20/5 Green Garden 27, North Manikdi, Dhaka Cantonment Dhaka-1206");
        user.setGender("Male");
        user.setPhoneNumber("01704670151");

        user.setPassword("1234");
        user.setTermsAccepted("true");


        Response res = baseController.editUserInfo(prop.getProperty("userId"), user);
        System.out.println(res.asString());
    }
        @Test(priority = 8,description = "Edit User Info WithOut Mandatory Field")
    public void editUserInfoWithOutMandatoryField(){
        UserModel user = new UserModel();
        user.setEmail("shabitalahi123+550@gmail.com");
        user.setFirstName("Shakib");
        user.setLastName("Rony");
        user.setAddress("20/5 Green Garden 27, North Manikdi, Dhaka Cantonment Dhaka-1206");
        user.setGender("Male");
        user.setPhoneNumber("01704670151");

        Response res = baseController.editUserInfo(prop.getProperty("userId"), user);
        System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();
        String actualError = jsonPath.get("error");
            Assert.assertTrue(actualError.contains("notNull Violation: User.password cannot be null"));
            Assert.assertTrue(actualError.contains("notNull Violation: User.termsAccepted cannot be null"));

    }
}
