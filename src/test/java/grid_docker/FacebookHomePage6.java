package grid_docker;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import utilities.BrowserFactory;

public class FacebookHomePage6 {
	
	@Test
	public void test6() throws Exception{
		
		WebDriver driver = BrowserFactory.startBrowser();
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/");
		
		System.out.println("test 6");
		System.out.println("title : "+driver.getTitle());
		
		driver.quit();
	}

}
