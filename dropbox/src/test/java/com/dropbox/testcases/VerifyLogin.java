package com.dropbox.testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.dropbox.pageobjects.BasePage;

public class VerifyLogin extends TestBase{
	BasePage basePage;
	@BeforeClass
	public void objectsIntialization() {
		basePage = PageFactory.initElements(driver, BasePage.class);
	}
	@Parameters({"usrName","usrPassword"})
	@Test
	public void verifyLogin(@Optional("usename")String uname, @Optional("password")String pwd) {
		System.out.println("test");
		basePage.lnkSigin.click();
		basePage.userLogin(uname, pwd);
		Assert.assertTrue(basePage.lnkUsrProfile.isDisplayed());
	}
}
