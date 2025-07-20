package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constant.AppConstant;
import com.qa.opencart.util.ExcelUtil;

public class ProductInfoPageTest extends BaseTest {
	@BeforeClass
	public void productSetUpPage() {
		homePage = loginPage.login("tej232@gmail.com", "Test@1234");
	}

	@DataProvider
	public Object[][] getHeaderData() {
		return new Object[][] { 
			{ "macbook", "MacBook Pro" }, 
			{ "macbook", "MacBook Air" }, 
			{ "canon", "Canon EOS 5D" }

		};
		
	}

	@Test(dataProvider="getHeaderData")
	public void getHeaderDetailsTest(String searchKey,String productName) {
		searchResultPage = homePage.doSearch(searchKey);
		productInfoPage = searchResultPage.SelectProduct(productName);
		String acHeaderDetails = productInfoPage.getHeaderDetails();
		Assert.assertEquals(acHeaderDetails, productName, "==Product header is incorrect==");
	}

	@DataProvider
	public Object[][] getProductImageData() {
		return new Object[][] { 
			{ "macbook", "MacBook Pro", 4 }, 
			{ "macbook", "MacBook Air", 4 },
			{ "imac", "iMac", 3 },
			{ "canon", "Canon EOS 5D", 3 } 
			};
	}
	@DataProvider
	public Object[][] getProductImageExcelData()
	{
		Object ProductData[][]=ExcelUtil.getTestData(AppConstant.PRODUCT_SHEET_NAME);
		return ProductData;
	}

	@Test(dataProvider = "getProductImageExcelData")
	public void getProductImageCountTest(String searchKey, String productName, String expectedImageCount) {
		searchResultPage = homePage.doSearch(searchKey);
		productInfoPage = searchResultPage.SelectProduct(productName);
		int actProductImageCount = productInfoPage.getProductImageCount();
		Assert.assertEquals(actProductImageCount, Integer.parseInt(expectedImageCount), "==Product count is not matched==");
	}
	@Test
	public void productInfoTest()
	{
		searchResultPage = homePage.doSearch("macbook");
		productInfoPage = searchResultPage.SelectProduct("MacBook Pro");
		Map<String,String> productInfoMap=productInfoPage.getProductInfo();
		productInfoMap.forEach((k,v) -> System.out.println(k+" "+v));
		SoftAssert softAssert = new SoftAssert();	
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(productInfoMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(productInfoMap.get("price"), "$2,000.00");
		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productInfoMap.get("Reward Points"), "800");
		softAssert.assertEquals(productInfoMap.get("header"), "MacBook Pro");
		softAssert.assertEquals(productInfoMap.get("imageCount"), "4");
		softAssert.assertAll();



		}
}
