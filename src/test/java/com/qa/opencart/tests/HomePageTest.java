package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constant.AppConstant;
import com.qa.opencart.constant.AppError;

public class HomePageTest extends BaseTest {

	
	@BeforeClass
	public void homePageSetup()
	{
		homePage=loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	}
	@Test
	public void homePageTitleTest()
	{
		String actTitle=homePage.getHomePageTitle();
		System.out.println(actTitle);
		Assert.assertEquals(actTitle, AppConstant.HOME_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);
	}
	@Test
	public void homePageUrlTest()
	{
		String actUrl=homePage.getHomePageUrl();
		System.out.println(actUrl);
		Assert.assertTrue(actUrl.contains(AppConstant.HOME_PAGE_URL_FRACTIONVALUE),AppError.URL_NOT_FOUND_ERROR);
	}
	@Test
	public void isLogoutLinkExistTest()
	{
		Assert.assertTrue(homePage.isLogoutLinkExist(),AppError.ELEMENT_NOT_FOUND_ERROR);
	}
	
	@Test
	public void getHeaderListTest()
	{
		List<String> allheaders=homePage.getHeaderList();
		System.out.println("home page headers are : "+allheaders);
	}
	
	@DataProvider
	public Object[][] getSearchData()
	{
		return new Object[][]
				{{"macbook",3},
			      {"imac",1},
			      {"samsung",2},
			      {"canon",1},
			      {"airtel",0},
			
				};
	}
	@Test(priority=Integer.MAX_VALUE ,dataProvider="getSearchData")
	public void doSearchTest(String searchKey,int expResultCount)
	{
		searchResultPage=homePage.doSearch(searchKey);
		int actResultCount=searchResultPage.getResultCount();
		Assert.assertEquals(actResultCount, expResultCount,"==count is not matched==");
	}
	
	
}
