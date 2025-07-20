package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.constant.AppConstant;
import com.qa.opencart.util.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	private By email_address=By.id("input-email");
	private By password_ele=By.id("input-password");
	private By login_btn=By.xpath("//input[@type='submit']");
	private By forgotPasswordLink=By.xpath("//label[text()='Password']/following-sibling::a[text()='Forgotten Password']");

	@Step("Getting Login page Title")
	public String getLoginTitle()
	{
		String title=eleUtil.waitforTitleIs(AppConstant.LOGIN_PAGE_TITLE,AppConstant.DEFAULT_TIME_OUT);
		System.out.println("Title of the Login page is : "+title);
		ChainTestListener.log("Title of the Login page is : "+title);

		return title;
	}
	@Step("Getting Login page URL")
	public String getLoginUrl()
	{
		String url=eleUtil.waitforURLcontains(AppConstant.LOGIN_PAGE_URL_FRACTIONVALUE,AppConstant.DEFAULT_TIME_OUT);
		System.out.println("Url of the Login page is : "+url);
		return url;
	}
	@Step("Getting forgotPassword Webelement")
	public boolean isforgotPasswordDisplayed()
	{
		return eleUtil.doIsElementDisplayed(forgotPasswordLink);
	}
	@Step("Getting Login")
	public HomePage login(String username,String password)
	{
		eleUtil.waitForWebElementVisible(email_address, AppConstant.DEFAULT_TIME_OUT).sendKeys(username);
		eleUtil.doSendkeys(password_ele, password);
		eleUtil.doClick(login_btn);
		return new HomePage(driver);
	}
}
