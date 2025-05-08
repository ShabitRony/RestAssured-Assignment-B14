package testrunner;

import controller.UserController;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import setup.ItemModel;
import setup.Setup;
import utils.Utils;

import java.util.PriorityQueue;
import java.util.Properties;

public class ItemTestRunner extends Setup {
    private UserController userController;

    @BeforeClass
    public void initUserController(){
        userController = new UserController(prop);
    }
    @Test(priority = 1,description = "Get All Item List")
    public void getItemList(){
        Response res = userController.getItemList();
        System.out.println(res.asString());
    }
    @Test(priority = 2,description = "Add New Item")
    public void addItem() throws ConfigurationException {
        ItemModel itemModel = new ItemModel();

        String amount ="500";
        String itemName ="Shirt";
        String month = "May";
        String purchaseDate ="2025-05-08";
        int quantity =1;
        String remarks ="Good";

        itemModel.setAmount(amount);
        itemModel.setItemName(itemName);
        itemModel.setMonth(month);
        itemModel.setPurchaseDate(purchaseDate);
        itemModel.setQuantity(quantity);
        itemModel.setRemarks(remarks);

        Response res = userController.addItem(itemModel);
        JsonPath jsonObj= res.jsonPath();
        String itemId = jsonObj.get("_id");
        System.out.println(itemId);
        Utils.setEnvVar("itemId",itemId);
    }
}
