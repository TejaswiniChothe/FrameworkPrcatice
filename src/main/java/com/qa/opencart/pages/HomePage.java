package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constant.AppConstant;
import com.qa.opencart.util.ElementUtil;

public class HomePage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		eleUtil=new ElementUtil(driver);

	}

	private By logoutLink = By.xpath("//a[text()='Logout' and @class='list-group-item']");
	private By headers = By.tagName("h2");
	private By search = By.xpath("//input[@name='search']");
	private By searchIcon = By.xpath("//button[@type='button' and @class='btn btn-default btn-lg']");

	public String getHomePageTitle() {
		String homePageTitle = eleUtil.waitforTitleIs(AppConstant.HOME_PAGE_TITLE,AppConstant.DEFAULT_TIME_OUT);
		System.out.println("title fo the home page is : " + homePageTitle);
		return homePageTitle;
	}

	public String getHomePageUrl() {
		String homePageUrl =eleUtil.waitforURLcontains(AppConstant.HOME_PAGE_URL_FRACTIONVALUE,AppConstant.DEFAULT_TIME_OUT);
		System.out.println("Url fo the home page is : " + homePageUrl);
		return homePageUrl;
	}

	public boolean isLogoutLinkExist() {
		return eleUtil.doIsElementDisplayed(logoutLink);
	}

	public List<String> getHeaderList() {
		List<WebElement> headersList = eleUtil.waitforElementVisble(headers, AppConstant.DEFAULT_TIME_OUT);
		List<String> headerValueList = new ArrayList<String>();
		for (WebElement e : headersList) {
			String headerText = e.getText();
			headerValueList.add(headerText);
		}
		return headerValueList;
	}

	public SearchResultPage doSearch(String searchKey) {
		
		WebElement searchEle=eleUtil.waitForWebElementPresence(search, AppConstant.DEFAULT_TIME_OUT);
		eleUtil.doSendkeys(searchEle,searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultPage(driver);

	}

}
