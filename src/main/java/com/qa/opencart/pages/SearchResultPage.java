package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constant.AppConstant;
import com.qa.opencart.util.ElementUtil;

public class SearchResultPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
		eleUtil=new ElementUtil(driver);

		
	}
	private By productResult=By.cssSelector("div.product-thumb");
	
	public int getResultCount()
	{
	   
		int resultCount=eleUtil.waitforElementVisble(productResult,AppConstant.DEFAULT_TIME_OUT).size();
		System.out.println("product count is :"+resultCount);
		return resultCount;
	}
	
	public ProductInfoPage SelectProduct(String productName)
	{
		System.out.println("ProductName is : "+productName);
		eleUtil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
		
	}
	
	
}
