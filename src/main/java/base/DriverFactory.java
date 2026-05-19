package base;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {

	private static WebDriver driver;

	public static WebDriver getDriver(String browser) {

		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			System.out.println("Chrome browser opened successfully");
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
			System.out.println("Edge browser opened successfully");
		} else {
			throw new IllegalArgumentException("browser is unsupported");
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}

	public static void quitDriver() {
		if (driver != null) {
			driver.quit();
			System.out.println("Browser is closed");
		}
	}
}
