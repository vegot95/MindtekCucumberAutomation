package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.HrAppLoginPage;
import utilities.ConfigReader;
import utilities.Driver;

public class HrAppSteps {
    WebDriver driver= Driver.getDriver();
    HrAppLoginPage hrAppLoginPage=new HrAppLoginPage();

    @Given("User navigates to HR App {string} Page")
    public void user_navigate_to_HR_App_Login_Page(){
        driver.get(ConfigReader.getProperty("HrAppQAURl"));
    }
    @When ("user enters {string} for username and {string} for password and clicks login button")
    public void user_enters_for_username_and_clicks_login_button(String username, String password){

    }
}
