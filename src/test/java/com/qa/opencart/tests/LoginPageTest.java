package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constant.AppConstant;
import com.qa.opencart.constant.AppError;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
@Epic("Epic 100:Design login page of OpenCart")
@Story("User Story : Design various feature for Open Cart Login page")
@Feature("Feature 50: Login Page Feature")
@Owner("Tejaswini Chothe")
public class LoginPageTest extends BaseTest {
	@Description("Checking Login Page Title")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void LoginPageTitleTest() {
		String actTitle = loginPage.getLoginTitle();
		ChainTestListener.log("Verifyng login page title");
		System.out.println("Title of the page is : " + actTitle);
		Assert.assertEquals(actTitle, AppConstant.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);

	}
	@Description("Checking Login Page URL")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void LoginPageUrlTest() {
		String actUrl = loginPage.getLoginUrl();
		System.out.println("Url of the page is : " + actUrl);
		Assert.assertTrue(actUrl.contains(AppConstant.LOGIN_PAGE_URL_FRACTIONVALUE), AppError.URL_NOT_FOUND_ERROR);

	}
	@Description("Checking Login Page ForgotPassword Link")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void isforgotPasswordDisplayedTest() {
		Assert.assertTrue(loginPage.isforgotPasswordDisplayed(), AppError.ELEMENT_NOT_FOUND_ERROR);

	}
	@Description("Checking Login Functionality")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = Integer.MAX_VALUE)
	public void loginTest() {
		homePage = loginPage.login(prop.getProperty("username"), System.getProperty("AppPassword"));
		String homePageTitle = homePage.getHomePageTitle();
		Assert.assertEquals(homePageTitle, AppConstant.HOME_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);
	}
	@Description("Checking Logo On Login Page")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description="checking log on the page")
	public void isLogoDisplayedTest() {
		Assert.assertTrue(commonPage.isLogoDisplayed(), AppError.LOGO_NOT_FOUND_ERROR);
	}

	@DataProvider
	public Object[][] getFooterData()
	{
		return new Object[][] {{"About Us"},{"Contact Us"},{"Brands"},{"My Account"}};
	}
	@Description("Checking footer On Login Page")
	@Severity(SeverityLevel.NORMAL)
	@Test(dataProvider="getFooterData")
	public void footerTest(String footerLink)
	{
		Assert.assertTrue((commonPage.checkFooterLink(footerLink)));
	}

}
