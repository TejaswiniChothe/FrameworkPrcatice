package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionManger {
	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;

	public OptionManger(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChromeOptions() {
		co=new ChromeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("==Running the chrome browser in headless mode==");
			co.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("==Running the chrome browser in incognito mode==");
			co.addArguments("--incognito");
		}
		return co;
	}
	public FirefoxOptions getFirefoxOptions() {
		fo=new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("==Running the FireFox browser in headless mode==");
			fo.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("==Running the FireFox browser in incognito mode==");
			fo.addArguments("--incognito");
		}
		return fo;
	}
	public EdgeOptions getEdgeOptions() {
		eo=new EdgeOptions();

		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("==Running the Edge browser in headless mode==");
			eo.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("==Running the Edge browser in incognito mode==");
			eo.addArguments("--inPrivate");
		}
		return eo;
	}

}
