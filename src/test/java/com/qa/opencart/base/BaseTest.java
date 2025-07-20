package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.chaintest.service.ChainPluginService;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.CommonPage;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.SearchResultPage;
//@Listeners(ChainTestListener.class)
public class BaseTest {

	WebDriver driver;
	DriverFactory df;
	protected LoginPage loginPage;
	protected HomePage homePage;
	protected SearchResultPage searchResultPage;
	protected ProductInfoPage productInfoPage;
	protected CommonPage commonPage;
	protected Properties prop;

	@Parameters("browser")
	@BeforeTest(description = "setup: init the driver and properties")
	public void setUp(String browserName)
	{
		df=new DriverFactory();
		prop=df.initProp();
		if(browserName!=null)
		{
			prop.setProperty("browser", browserName);
		}
		driver=df.initDriver(prop);
		loginPage=new LoginPage(driver);
		commonPage=new CommonPage(driver);
		
		ChainPluginService.getInstance().addSystemInfo("Build#","1.0");
		ChainPluginService.getInstance().addSystemInfo("Owner#","Naveen Automation Labs");
		ChainPluginService.getInstance().addSystemInfo("headless#",prop.getProperty("headless"));
		ChainPluginService.getInstance().addSystemInfo("incognito#",prop.getProperty("incognito"));

	}
	@AfterMethod
	public void attachScreenshot(ITestResult result)
	{
		if(!result.isSuccess()) {
		ChainTestListener.embed(DriverFactory.getScreenshotBase64(), "image/png");
		}
	}
	
	@AfterTest(description = "close browser propeties")
	public void tearDown()
	{
		driver.close();
	}
}

