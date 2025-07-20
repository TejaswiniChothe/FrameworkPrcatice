package com.qa.opencart.pages;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constant.AppConstant;
import com.qa.opencart.util.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String,String> productMap;
 

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil=new ElementUtil(driver);

	}
	
	private By header=By.tagName("h1");
	private By productImage=By.cssSelector("div.col-sm-8 a.thumbnail img");
	private By productMetaData=By.xpath("(//div[@id='content']//child::ul[@class='list-unstyled'])[1]/li");
	private By priceMetaData=By.xpath("(//div[@id='content']//child::ul[@class='list-unstyled'])[2]/li");
	public String getHeaderDetails()
	{
		String productHeaderText=eleUtil.getElementText(header);
		System.out.println("product header text is : "+productHeaderText);
		return productHeaderText;
	}
	
	//To get Product image count
	public int getProductImageCount()
	{
		int productImageCount=eleUtil.waitforElementVisble(productImage,AppConstant.DEFAULT_TIME_OUT).size();
		System.out.println("Product Image count is: "+productImageCount);
		return productImageCount;
	}
	//
	//To get product meta data
	private void getProductMeatData()
	{
		List<WebElement> metaList=eleUtil.waitforElementVisble(productMetaData,AppConstant.DEFAULT_TIME_OUT);
		for(WebElement e:metaList)
		{
			String metaText=e.getText();
			String meta[]=metaText.split(":");
			String metaKey=meta[0].trim();
			String metValue=meta[1].trim();
			productMap.put(metaKey, metValue);
		}
	}
	//To get price meta data
	private void getPriceMeatData()
	{
		List<WebElement> priceList=eleUtil.waitforElementVisble(priceMetaData,AppConstant.DEFAULT_TIME_OUT);
		String productPrice=priceList.get(0).getText().trim();
		String productExTx=priceList.get(1).getText().split(":")[1].trim();
		productMap.put("price", productPrice);
		productMap.put("ExTx", productExTx);


	}
	//To getProductInfo
	public Map<String, String> getProductInfo()
	{
		//productMap=new HashMap<String,String>();
    	//productMap=new LinkedHashMap<String,String>();
		productMap=new TreeMap<String,String>();
		productMap.put("header", getHeaderDetails());
		productMap.put("imageCount", getProductImageCount()+"");
		getProductMeatData();
		getPriceMeatData();
		return productMap;
	}
	
}
