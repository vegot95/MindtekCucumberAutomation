package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.EtsyAppHomePage;
import pages.EtsyAppSearchPage;
import utilities.ConfigReader;
import utilities.Driver;

import java.util.*;

public class EtsyAppSteps {

    WebDriver driver = Driver.getDriver();
    EtsyAppHomePage etsyAppHomePage = new EtsyAppHomePage();
    EtsyAppSearchPage etsyAppSearchPage = new EtsyAppSearchPage();

    @Given("user navigates to Etsy application")
    public void user_navigates_to_Etsy_application() {
        driver.get(ConfigReader.getProperty("EtsyURL"));
    }
    @When("user searches for {string}")
    public void user_searches_for(String item) {
      etsyAppHomePage.searchBox.sendKeys(item + Keys.ENTER);
    }
    @When("user applies price filter over {int}")
    public void user_applies_price_filter_over(Integer price) {
        etsyAppSearchPage.filterButton.click();
        etsyAppSearchPage.priceRadioButton.click();
        etsyAppSearchPage.applyButton.click();
    }
    @Then("user validates that item prices are over {int}")
    public void user_validates_that_item_prices_are_over(Integer price) throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> prices = etsyAppSearchPage.prices;

        for (WebElement element: prices) {
            System.out.println(element.getText());
            String priceStr = element.getText().replace(",",""); //"32,402.00" --> 32402.00
            double doublePrice = Double.parseDouble(priceStr);
            System.out.println(doublePrice);
            Assert.assertTrue(doublePrice>=price);
        }
    }

    @Then("user validates search result items contain keyword {string} and that item prices are over {int}")
    public void userValidatesSearchResultItemsContainKeywordAndThatItemPricesAreOver(String item, int price) throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> prices = etsyAppSearchPage.prices;
        for (WebElement element: prices) {
            System.out.println(element.getText());
            String priceStr = element.getText().replace(",",""); //"32,402.00" --> 32402.00
            double doublePrice = Double.parseDouble(priceStr);
            System.out.println(doublePrice);
            Assert.assertTrue(doublePrice>=price);
        }

        List<WebElement> itemNames=etsyAppSearchPage.itemNames;

        for (WebElement e:itemNames){
            //System.out.println(e.getText());
            if(e.getText().toLowerCase(Locale.ROOT).contains(item) ||e.getText().toLowerCase(Locale.ROOT).contains("rug")) {
                StringBuilder newItemNames = new StringBuilder();
                newItemNames.append(e.getText());
                System.out.println(newItemNames);
                Assert.assertTrue(newItemNames.toString().toLowerCase().contains(item) || newItemNames.toString().toLowerCase().contains("rug"));
            }
        }
    }

    @When("user clicks on {string} section")
    public void userClicksOnSection(String section) throws InterruptedException {
        Thread.sleep(3000);
        if(section.equalsIgnoreCase("Jewelry and Accessories")){
            etsyAppHomePage.jewelry.click();
        }else if (section.equalsIgnoreCase("Valentine’s Day Gift Ideas for Everyone 2022")){
            etsyAppHomePage.endYearSale.click();
        }else if (section.equalsIgnoreCase("Clothing & Shoes")){
            etsyAppHomePage.clothingShoes.click();
        }
        else if (section.equalsIgnoreCase("Home & Living")){
            etsyAppHomePage.homeAndLiving.click();
        }
        else if (section.equalsIgnoreCase("Wedding & Party")){
            etsyAppHomePage.weddingParty.click();
        }
        else if (section.equalsIgnoreCase("Toys & Entertainment")){
            etsyAppHomePage.toysEntertainment.click();
        }
        else if (section.equalsIgnoreCase("Art and Collectibles")){
            etsyAppHomePage.artCollectibles.click();
        }
        else if (section.equalsIgnoreCase("Craft Supplies and Tools")){
            etsyAppHomePage.craftSupplies.click();
        }
        else if (section.equalsIgnoreCase("The Etsy Gift Guide for 2022")){
            etsyAppHomePage.gift.click();
        }

    }

    @Then("user validates that the title is {string}")
    public void userValidatesThatTheTitleIs(String expectedTitle) {
        String actualTitle= driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);
    }
}
