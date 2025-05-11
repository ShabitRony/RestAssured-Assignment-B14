package testrunner;

import controller.BaseController;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import setup.ItemModel;
import setup.Setup;
import utils.Utils;

public class ItemTestRunner extends Setup {
    private BaseController baseController;

    @BeforeClass
    public void initUserController(){
        baseController = new BaseController(prop);
    }
    @Test(priority = 1,description = "Get All Item List with Invalid Authorization")
    public void getItemListWithInvalidAuthorization(){
        Response res = baseController.getItemListWithInvalidAuthorization();
        System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();
        String actualMessage = jsonPath.get("message");
        String expectedMessage = "Not authorized, token failed";
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test(priority = 2,description = "Get All Item List")
    public void getItemList(){
        Response res = baseController.getItemList();
        System.out.println(res.asString());
    }
    @Test(priority = 3,description = "Add New Item")
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

        Response res = baseController.addItem(itemModel);
        JsonPath jsonObj= res.jsonPath();
        String itemId = jsonObj.get("_id");
        System.out.println(itemId);
        Utils.setEnvVar("itemId",itemId);
    }
    @Test(priority = 4, description = "Edit Item Name")
    public void editItemInfo() {
        ItemModel item = new ItemModel();
        item.setAmount("500");
        item.setItemName("Pant");
        item.setMonth("May");
        item.setPurchaseDate("2025-05-08");
        item.setQuantity(1);
        item.setRemarks("Updated item details");

        Response res = baseController.editItemInfo(prop.getProperty("itemId"), item);
        System.out.println(res.asString());
    }
    @Test(priority = 5,description = "Delete Item")
    public void deleteItem(){
        Response res =  baseController.deleteItem(prop.getProperty("itemId"));
        System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();
        String actualMessage = jsonPath.get("message");
        String expectedMessage = "Cost deleted successfully";
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test(priority = 6,description = "Item Not Found")
    public void ItemNotFound(){
        Response res =  baseController.deleteItem(prop.getProperty("itemId"));
        System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();
        String actualMessage = jsonPath.get("message");
        String  expectedMessage ="Cost not found";
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

}
