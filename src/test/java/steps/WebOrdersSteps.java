package steps;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import pages.WebOrdersHomePage;
import pages.WebOrdersLoginPage;
import pages.WebOrdersOrderPage;
import utilities.BrowserUtils;
import utilities.ConfigReader;
import utilities.Driver;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WebOrdersSteps {
    WebDriver driver = Driver.getDriver();
    WebOrdersLoginPage webOrdersLoginPage = new WebOrdersLoginPage();
    WebOrdersHomePage webOrdersHomePage = new WebOrdersHomePage();
    WebOrdersOrderPage webOrdersOrderPage = new WebOrdersOrderPage();
    int numOfRows;
    List<Map<String, Object>> data;


    @Given("user navigates to the weborders application")
    public void user_navigates_to_the_weborders_application() {
        driver.get(ConfigReader.getProperty("WebOrdersURL"));
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @When("user provides username {string} and password {string} clicks on login button")
    public void user_provides_username_and_password_clicks_on_login_button(String username, String password) {
        webOrdersLoginPage.username.sendKeys(username);
        webOrdersLoginPage.password.sendKeys(password);
        webOrdersLoginPage.loginButton.click();
    }

    @Then("user validates application is logged in")
    public void user_validates_application_is_logged_in() {
        String actualTitle = driver.getTitle();
        String expectedTitle = "Web Orders";

        Assert.assertEquals(expectedTitle, actualTitle);
        driver.quit();

    }

    @Then("user validates error message {string}")
    public void user_validates_error_message(String errorMessage) {
        String actualErrorMessage = webOrdersLoginPage.errorMessage.getText();
        Assert.assertEquals(errorMessage, actualErrorMessage);
        driver.quit();
    }


    @And("user clicks on Order module")
    public void userClicksOnOrderModule() {
        webOrdersHomePage.orderModule.click();

    }

    //for {int} we should do ourselves
    @And("user selects {string} product with {int} quantity")
    public void userSelectsProductWithQuantity(String product, int quantity) {
        BrowserUtils.selectByValue(webOrdersOrderPage.orderProductDropdown, product);
        webOrdersOrderPage.quantityBox.sendKeys(Keys.BACK_SPACE); //deletes 0;
        webOrdersOrderPage.quantityBox.sendKeys(quantity + "" + Keys.ENTER);
        // sendKeys accepts only string that's why we put empty string in front of int

    }

    //for {int} we should do ourselves
    @Then("user validates total is calculated correctly for quantity {int}")
    public void userValidatesTotalIsCalculatedCorrectlyForQuantityQuantity(int quantity) {
        String pricePerUnit = webOrdersOrderPage.pricePerUnit.getAttribute("value");
        int expectedTotal = 0;
        String discountAmount = webOrdersOrderPage.disCount.getAttribute("value");
        int discountAmountInt = Integer.parseInt(discountAmount);
        int pricePerUnitInt = Integer.parseInt(pricePerUnit);

        if (discountAmountInt == 0) {
            expectedTotal = quantity * pricePerUnitInt;
        } else {
            expectedTotal = quantity * pricePerUnitInt;
            expectedTotal = expectedTotal - expectedTotal * discountAmountInt / 100;
        }
        String actualTotalAmount = webOrdersOrderPage.total.getAttribute("value");
        int actualTotal = Integer.parseInt(actualTotalAmount);
        Assert.assertEquals(expectedTotal, actualTotal);
    }

    @And("user counts number of orders in table")
    public void userCountsNumberOfOrdersInTable() {
        numOfRows = webOrdersHomePage.numberOfRows.size();

    }

    @And("user creates order with data")
    public void userCreatesOrderWithData(DataTable dataTable) throws InterruptedException {
        data = dataTable.asMaps(String.class, Object.class);
        System.out.println(data.get(0).get("order"));
        BrowserUtils.selectByValue(webOrdersOrderPage.orderProductDropdown, data.get(0).get("order").toString());
        webOrdersOrderPage.quantityBox.sendKeys(Keys.BACK_SPACE);
        webOrdersOrderPage.quantityBox.sendKeys(data.get(0).get("quantity").toString());
        webOrdersOrderPage.name.sendKeys(data.get(0).get("name").toString());
        webOrdersOrderPage.street.sendKeys(data.get(0).get("street").toString());
        webOrdersOrderPage.city.sendKeys(data.get(0).get("city").toString());
        webOrdersOrderPage.state.sendKeys(data.get(0).get("state").toString());
        webOrdersOrderPage.zip.sendKeys(data.get(0).get("zip").toString());
        webOrdersOrderPage.visaCheckBox.click();
        webOrdersOrderPage.cardNumber.sendKeys(data.get(0).get("cc").toString());
        webOrdersOrderPage.expireDate.sendKeys(data.get(0).get("expire date").toString());
        webOrdersOrderPage.processButton.click();
        Thread.sleep(1000);

    }

    @Then("user validates success message {string}")
    public void userValidatesSuccessMessage(String expectedMessage) {
        String actualMessage = webOrdersOrderPage.successMessage.getText();
        Assert.assertEquals(expectedMessage, actualMessage);

    }

    @And("user validates order added to List of Orders")
    public void userValidatesOrderAddedToListOfOrders() throws InterruptedException {
        webOrdersHomePage.viewAllOrderModule.click();
        Thread.sleep(3000);
        int afterOrder = webOrdersHomePage.numberOfRows.size();
        System.out.println(afterOrder);
        System.out.println(numOfRows);
        Assert.assertEquals(afterOrder - numOfRows, 1);

        String actualName = webOrdersHomePage.firstNameInRow.getText();
        Assert.assertEquals(data.get(0).get("name").toString(), actualName);

    }

}
