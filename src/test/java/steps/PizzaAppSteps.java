package steps;

//import io.cucumber.core.gherkin.vintage.internal.gherkin.ast.DataTable;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.PizzaAppPage;
import utilities.BrowserUtils;
import utilities.ConfigReader;
import utilities.Driver;

import java.util.Map;

public class PizzaAppSteps {
    WebDriver driver= Driver.getDriver();
    Map<String,Object>data;
    PizzaAppPage pizzaAppPage=new PizzaAppPage();
    String cost;

    @Given("user navigates to pizza application")
    public void user_navigates_to_pizza_application() {
        driver.get(ConfigReader.getProperty("PizzaAppURL"));
    }


    @When("user creates pizza order with data")
    public void user_creates_pizza_order_with_data(DataTable dataTable) {
        data = dataTable.asMap(String.class, Object.class);
//        for(Object object:data.values()){
        BrowserUtils.selectByValue(pizzaAppPage.pizza1Box, data.get("Pizza").toString());
        BrowserUtils.selectByValue(pizzaAppPage.toppings1Box, data.get("Toppings 1").toString());
        BrowserUtils.selectByValue(pizzaAppPage.toppings2Box, data.get("Toppings 2").toString());
        pizzaAppPage.pizza1QtyBox.sendKeys(data.get("Quantity").toString());
        pizzaAppPage.nameBox.sendKeys(data.get("Name").toString());
        pizzaAppPage.emailBox.sendKeys(data.get("Email").toString());
        pizzaAppPage.phoneBox.sendKeys(data.get("Phone").toString());
        if(data.get("Payment Type").toString().equalsIgnoreCase("Cash on PickUp")){
            pizzaAppPage.cashpaymentRadioButton.click();
        }else if(data.get("Payment Type").toString().equalsIgnoreCase("Credit Card")){
            pizzaAppPage.ccpaymentRadioButton.click();
        }
        //pizzaAppPage.cashpaymentRadioButton.click();
        cost=pizzaAppPage.pizza1CostBox.getAttribute("value");
        pizzaAppPage.placeOrderButton.click();


    }

    @Then("user validates that is created with success  message {string} {string}")
    public void userValidatesThatIsCreatedWithSuccessMessage(String success, String pizza) {
        String expectedSuccessMessage=success+cost+" "+pizza;
        String actualSuccessMessage=pizzaAppPage.dialogWindow.getText();
        Assert.assertEquals(expectedSuccessMessage,actualSuccessMessage);
    }
}
