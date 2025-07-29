package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.opecart.exception.FrameworkException;
import com.qa.opencart.constant.AppConstant;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionManger optionManger;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	public static String highlight;
	private static final Logger log = LogManager.getLogger(DriverFactory.class);

	public WebDriver initDriver(Properties prop)

	{
		String browserName = prop.getProperty("browser");
		optionManger = new OptionManger(prop);
		highlight = prop.getProperty("highlight");
		boolean remoteExecution = Boolean.parseBoolean(prop.getProperty("remote"));
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			if (remoteExecution) {
				initRemoteDriver("chrome");

			} else {
				tlDriver.set(new ChromeDriver(optionManger.getChromeOptions()));

			}
			break;
		case "firefox":
			if (remoteExecution) {
				initRemoteDriver("firefox");

			} else {
				tlDriver.set(new FirefoxDriver(optionManger.getFirefoxOptions()));

			}

			break;
		case "edge":
			if (remoteExecution) {
				initRemoteDriver("edge");

			} else {
				tlDriver.set(new EdgeDriver(optionManger.getEdgeOptions()));

			}
			break;
		default:
			// System.out.println("please pass correct browser : " + prop);
			log.error("please pass correct browser :" + prop);
			throw new FrameworkException("==Invalid browser==");
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();

	}

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	public Properties initProp() {
		String envName = System.getProperty("env");

		// System.out.println("Running the test suit on env : " + envName);
		log.info("Running the test suit on env : " + envName);

		FileInputStream ip = null;
		prop = new Properties();
		try {
			if (envName == null) {
				// System.out.println("No env is passed hence running the test case on Qa env");
				log.warn("No env is passed hence running the test case on Qa env");
				ip = new FileInputStream(AppConstant.CONFIG_QA_PROP_FILE_PATH);
			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream(AppConstant.CONFIG_QA_PROP_FILE_PATH);
					break;
				case "uat":
					ip = new FileInputStream(AppConstant.CONFIG_UAT_PROP_FILE_PATH);
					break;
				case "stage":
					ip = new FileInputStream(AppConstant.CONFIG_STAGE_PROP_FILE_PATH);
					break;

				default:
					// System.out.println("Please pass correct enviroment : " + envName);
					log.error("Please pass correct enviroment : " + envName);
					throw new FrameworkException("==Invalid enviroment==");

				}
			}
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

//	public Properties initProp()
//	{
//		prop=new Properties();
//		try {
//			FileInputStream ip=new FileInputStream(AppConstant.CONFIG_PROP_FILE_PATH);
//			prop.load(ip);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return prop;
//	}

	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		String path = System.getProperty("user.dir") + "/screenshot/" + "_" + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		return srcFile;
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
	}

	private void initRemoteDriver(String browserName) {
		System.out.println("Running tests on grid: " + browserName);

		try {
			switch (browserName.trim().toLowerCase()) {
			case "chrome":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionManger.getChromeOptions()));
				break;
			case "firefox":
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionManger.getFirefoxOptions()));
				break;
			case "edge":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionManger.getEdgeOptions()));
				break;
			default:
				System.out.println("browser is not supported on GRID... " + browserName);
				break;
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();

		}

	}

}
