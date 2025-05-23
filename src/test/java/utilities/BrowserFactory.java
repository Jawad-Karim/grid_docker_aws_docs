package utilities;

import java.net.URI;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserFactory {

	public static WebDriver startBrowser() throws Exception {

		WebDriver driver = null;
		String execution_env = PropertyReader.getPropertyData("exec_env");
		String browserName = PropertyReader.getPropertyData("browser");
		String hubUrl = PropertyReader.getPropertyData("dockerHubUrl");
		URL HubUrl = new URI(hubUrl).toURL();

		switch (execution_env) {
		case "cloud":
			if (browserName.equalsIgnoreCase("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless=new");
				driver = new RemoteWebDriver(HubUrl, options);
			} 
			else if (browserName.equalsIgnoreCase("firefox")) {
				FirefoxOptions options = new FirefoxOptions();
				options.addArguments("--headless=new");
				driver = new RemoteWebDriver(HubUrl, options);
			} 
			else if (browserName.equalsIgnoreCase("edge")) {
				EdgeOptions options = new EdgeOptions();
				options.addArguments("--headless=new");
				driver = new RemoteWebDriver(HubUrl, options);
			} 
			else {
				System.out.println("Browser Name didn't match");
			}

			break;

		case "local":
			if (browserName.equalsIgnoreCase("chrome")) {
				driver = new ChromeDriver();
			} else if (browserName.equalsIgnoreCase("firefox")) {
				driver = new FirefoxDriver();
			} else if (browserName.equalsIgnoreCase("edge")) {
				driver = new EdgeDriver();
			} else {
				System.out.println("Enviroment Name didn't match");
			}
			break;

		default:
			break;
		}
		return driver;

	}

}
