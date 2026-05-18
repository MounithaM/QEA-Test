package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Locators
    @FindBy(id = "search-box-input")
    WebElement searchBox;

    @FindBy(css = "div.sort-drop")
    WebElement sortDropdown;

    @FindBy(css = "li[data-sorttype='plrty']")
    WebElement popularityOption;

    public void openSnapdeal() {
        driver.get("https://www.snapdeal.com/");
    }

    public void searchProduct(String productName) {
        searchBox.clear();
        searchBox.sendKeys(productName);
        searchBox.sendKeys(Keys.ENTER);
        System.out.println(productName +" is entered in searchBox");
    }

    public void sortByPopularity() {
        sortDropdown.click();
        popularityOption.click();
        System.out.println("Sorted by popularity");
    }
}