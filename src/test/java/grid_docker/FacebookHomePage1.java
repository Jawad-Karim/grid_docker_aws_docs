package grid_docker;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import utilities.BrowserFactory;

public class FacebookHomePage1 {
	
	@Test
	public void test1() throws Exception{
		
		WebDriver driver = BrowserFactory.startBrowser();
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/");
		
		System.out.println("test 1");
		System.out.println("title : "+driver.getTitle());
		
		driver.quit();
		
		
	}

}
