package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultsPage {

    WebDriver driver;

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Price filter fields
    @FindBy(name = "fromVal")
    WebElement minPrice;

    @FindBy(name = "toVal")
    WebElement maxPrice;

    @FindBy(css = "div.price-go-arrow")
    WebElement goButton;

    public void applyPriceFilter(String min, String max) {
        minPrice.clear();
        minPrice.sendKeys(min);
        maxPrice.clear();
        maxPrice.sendKeys(max);
        goButton.click();
        System.out.println("Price range is selected");
    }

    // 🔹 Product name cleaning logic
    private String cleanProductName(String rawName) {

        if (rawName.contains("|")) {
            return rawName.split("\\|")[0].trim();
        } else if (rawName.toLowerCase().contains("(tws)")) {
            int idx = rawName.toLowerCase().indexOf("(tws)") + 5;
            return rawName.substring(0, idx).trim();
        } else if (rawName.contains(",")) {
            return rawName.split(",")[0].trim();
        } else {
            return rawName.trim();
        }
    }

    // 🔹 Fetch Top 5 Products (Stale‑safe)
    public void printTop5Products() {

        List<String> names = new ArrayList<>();
        List<Integer> prices = new ArrayList<>();
        int index = 0;

        while (names.size() < 5) {
            try {
                List<WebElement> products =
                        driver.findElements(By.cssSelector("div.product-tuple-description"));

                WebElement product = products.get(index);

                String priceText = product
                        .findElement(By.cssSelector("span.product-price"))
                        .getText()
                        .replaceAll("[^0-9]", "");

                int price = Integer.parseInt(priceText);

                if (price >= 700 && price <= 1400) {
                    String rawName = product.findElement(By.tagName("p")).getText();
                    names.add(cleanProductName(rawName));
                    prices.add(price);
                }
                index++;

            } catch (StaleElementReferenceException e) {
                continue;
            }
        }

        System.out.println("\nTop 5 Bluetooth Headphones (₹700–₹1400):");
        for (int i = 0; i < names.size(); i++) {
            System.out.println((i + 1) + ". " + names.get(i) + " - ₹" + prices.get(i));
        }
    }
}