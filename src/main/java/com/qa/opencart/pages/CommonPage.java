package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constant.AppConstant;
import com.qa.opencart.util.ElementUtil;

public class CommonPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public CommonPage(WebDriver driver)
	{
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	private By logoImg=By.xpath("//img[contains(@src,'opencart-logo.png')]");
	private By footer=By.xpath("//footer//a");
	
	public boolean isLogoDisplayed()
	{
		return eleUtil.doIsElementDisplayed(logoImg);
	}
	public List<String> getFooterList()
	{
		List<WebElement> AllfooterList=eleUtil.waitforElementVisble(footer, AppConstant.DEFAULT_TIME_OUT);
		System.out.println("Size of the Footer is : "+AllfooterList.size());
		List<String> footerList=new ArrayList<String>();
		for(WebElement e:AllfooterList)
		{
			String footerText=e.getText();
			footerList.add(footerText);
		}
		return footerList;
	}
	
	public boolean checkFooterLink(String footerLink)
	{
		return getFooterList().contains(footerLink);
	}
}
