package testrunner;

import controller.UserController;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import setup.Setup;
import setup.UserModel;
import utils.Utils;

public class AdminTestRunner extends Setup {
    private UserController userController;

    @BeforeClass
    public void initUserController(){
        userController = new UserController(prop);
    }
    @Test(priority = 1, description = "User Login")
    public void adminLogin() throws ConfigurationException {
        UserModel userModel = new UserModel();
        userModel.setEmail("admin@test.com");
        userModel.setPassword("admin123");
        Response res = userController.adminLogin(userModel);
        JsonPath jsonObj= res.jsonPath();
        String token = jsonObj.get("token");
        System.out.println(token);
        Utils.setEnvVar("token",token);
    }
    @Test(priority = 2,description = "Get User List")
    public void getUserList() throws ConfigurationException {
        Response res =  userController.getUserList();
        System.out.println(res.asString() );
    }
    @Test(priority = 3,description = "Search User By Id")
    public void searchUser() throws ConfigurationException {
        Response res =  userController.searchUser(prop.getProperty("userId"));
        System.out.println(res.asString() );
    }
    @Test(priority = 4,description = "Edit User Info")
    public void editUserInfo(){
    Response res = userController.editUserInfo(prop.getProperty("userId"));
        System.out.println(res.asString());
    }
}
