package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {
	
	public static String getPropertyData(String expectedData) throws Exception{
		
		File file = new File("./propertyFiles/config.properties");
		FileInputStream fis = new FileInputStream(file);
		Properties pro = new Properties();
		pro.load(fis);
		String data = pro.getProperty(expectedData);
		
		
		return data;
	}

}
