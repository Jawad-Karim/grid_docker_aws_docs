package grid_docker;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import utilities.BrowserFactory;

public class FacebookHomePage4 {
	
	@Test
	public void test4() throws Exception{
		
		WebDriver driver = BrowserFactory.startBrowser();
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/");
		
		System.out.println("test 4");
		System.out.println("title : "+driver.getTitle());
		
		driver.quit();
	}

}
