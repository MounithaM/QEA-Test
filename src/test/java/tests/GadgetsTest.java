package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;

import base.DriverFactory;
import pages.HomePage;
import pages.ResultsPage;
import utils.ExcelReader;
import utils.ExtentReportManager;

public class GadgetsTest {

    WebDriver driver;
    HomePage homePage;
    ResultsPage resultsPage;
    ExcelReader excel;

    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {

        // ✅ INITIALIZE EXTENT REPORTS (MISSING EARLIER)
        extent = ExtentReportManager.getReport();
        test = extent.createTest("Top 5 Bluetooth Headphones Test");

        excel = new ExcelReader("src/test/resources/GadgetsData.xlsx", "Sheet1");

        driver = DriverFactory.getDriver(browser);

        homePage = new HomePage(driver);
        resultsPage = new ResultsPage(driver);

        test.info("Browser launched: " + browser);
    }

    @Test
    public void getTopFiveBluetoothHeadphones() {

        try {
            String keyword = excel.getCellData(1, 1);
            String minPrice = excel.getCellData(1, 2);
            String maxPrice = excel.getCellData(1, 3);

            homePage.openSnapdeal();
            test.info("Opened Snapdeal");

            homePage.searchProduct(keyword);
            test.info("Searched for: " + keyword);

            homePage.sortByPopularity();
            test.info("Sorted by popularity");

            resultsPage.applyPriceFilter(minPrice, maxPrice);
            test.info("Applied price filter: ₹" + minPrice + " - ₹" + maxPrice);

            resultsPage.printTop5Products();
            test.pass("Top 5 products fetched successfully");

        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @AfterClass
    public void tearDown() {

        DriverFactory.quitDriver();
        test.info("Browser closed");

        // ✅ NOW SAFE TO FLUSH
        extent.flush();
    }
}
