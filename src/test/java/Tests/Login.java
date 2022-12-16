package Tests;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.Base;
import io.github.bonigarcia.wdm.WebDriverManager;
import util.DataUtil;
import util.MyXLSReader;

public class Login extends Base {
	public WebDriver driver;
	public 	MyXLSReader excelReader;

	@Test(dataProvider = "data_Supplier")
	public void login_test(HashMap<String,String>hMap) 
	{
		
		if (!DataUtil.isRunnable(excelReader, "LoginTest", "Testcases")||hMap.get("Runmode").equals("N")) {
			throw new SkipException("Run mode is set to No,");
			
		}
		driver=	openBrowser(hMap.get("Browser"));
		driver.get(prop.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a/span[2]")).click();
		driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[2]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"input-email\"]")).sendKeys(hMap.get("Username"));
		driver.findElement(By.xpath("//*[@id=\"input-password\"]")).sendKeys(hMap.get("Password"));
		driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/div/form/input")).click();
		String expectedResult =hMap.get("ExpectedResult");
		boolean expectedConvertResult = false;
		if (expectedResult.equalsIgnoreCase("Success")) {
			expectedConvertResult=true;
		}else if (expectedResult.equalsIgnoreCase("Failure")) {
			expectedConvertResult=false;
		}
		boolean actualResult=false;
		try {
			 actualResult = driver.findElement(By.linkText("Edit your account information")).isDisplayed();
		} catch (Throwable e) {
			actualResult=false;		}
		Assert.assertEquals(actualResult, expectedConvertResult);
		driver.quit();
		
		
	}
	
	
	@DataProvider()
		public Object [][] data_Supplier() {
		Object[][] myData=null;
		try {
		 excelReader = new MyXLSReader("src\\test\\resources\\TutorialsNinja.xlsx");
		 myData = DataUtil.getTestData(excelReader, "LoginTest", "Data");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return myData;
	}
	
	
//	@DataProvider()
//	public Object[][]  data_Supplier() {
//		Object[][] myData = {{"ahmadibrahim502@yahoo.com","1234512345"},
//				{"ahmadalhamad242@yahoo.com","123123"}};
//		return myData;
//	}
	


}
