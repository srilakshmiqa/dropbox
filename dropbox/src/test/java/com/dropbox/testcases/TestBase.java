package com.dropbox.testcases;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class TestBase {

	WebDriver driver;
	protected WebDriverWait wait = null;
	@Parameters({"browserName","browserLocation"})
	@BeforeSuite
	public void driveSetup(@Optional("chrome")String  bname,String  browserPath) {

		switch(bname.toLowerCase()) {
		case "chrome":
			//System.setProperty("webdriver.safari.driver", "/usr/local/bin/SafariDriver");
			//System.setProperty("webdriver.safari.driver", browserPath);
			//driver = new SafariDriver();
			System.setProperty("webdriver.chrome.driver", browserPath);
			driver = new ChromeDriver();

			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", "E:\\AutomationWorkSpace\\internetexplorer.exe");
			driver = new InternetExplorerDriver();
			break;
		default:

			System.setProperty("webdriver.geko.driver", "E:\\AutomationWorkSpace\\geko.exe");
			driver = new FirefoxDriver();
			break;
		}
		driver.get("https://www.dropbox.com/login");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	@AfterMethod()
	public void captureScreenShots(ITestResult result) throws Exception {

		String screenShotName = null;

		try {
			if (ITestResult.FAILURE == result.getStatus()) {
				if (result.getParameters() != null && result.getParameters().length > 0) {
					screenShotName = (String) result.getMethod().getXmlTest().getName() + "_"
							+ result.getMethod().getMethodName() + "_" + (String) result.getParameters()[0];
				} else {
					screenShotName = (String) result.getMethod().getXmlTest().getName() + "_"
							+ result.getMethod().getMethodName();
				}
				takeScreenshot(screenShotName);
			}

		} catch (Exception e) {

			System.out.println("Exception while taking screenshot " + e.getMessage());
		}

	}
	@AfterSuite
	public void tearDown() {
		driver.quit();

	}

	public  boolean takeScreenshot(final String name) throws Exception {

		String destDir = "";
		destDir = "screenshots/failed";
		//destDir = "target/surefire-reports/screenshots";
		new File(destDir).mkdirs();
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		System.out.println("Taken Screenshot");
		return screenshot.renameTo(new File(destDir, String.format("%s.png", name)));


	}
}
