package com.automationScript;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.DriverSetup.DriverSetup;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.testScript.*;


public class BaseUI {
	static WebDriver driver = null;
	public static String data[];
	public static Properties prop = null;
	String baseUrl;
	//Method to read browser from excel and to create the driver for the same.
	public void openBrowserAndNavigateToPracto() throws IOException, InterruptedException, AWTException {
		if (prop == null) {
			prop = new Properties();
				//Open the property file 
				FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\resources\\projectConfig.properties");
				prop.load(file);
		}
		  String browser = prop.getProperty("browser");
		  	baseUrl = prop.getProperty("url");
		  	driver = DriverSetup.getWebDriver(browser);
			driver.manage().window().maximize();
			driver.get(baseUrl);
			// Verify if correct page is loaded
			String pageTitle = driver.getTitle();
			Assert.assertEquals(pageTitle,
					"Practo | Video Consultation with Doctors, Book Doctor Appointments, Order Medicine, Diagnostic Tests");
			
	}
	//if correct page is loaded pass the status as "PASS" else pass the status as "FAIL"
	ExtentTest logger1 = TestClass.logger;
	public void reportPass(String comments) {
		logger1.log(Status.PASS,comments);
		//Assert.assertTrue(true);
	}
	public void reportFail(String comments) {
		logger1.log(Status.FAIL,comments);
		//Assert.assertTrue(false);
		
	}
	//Method to take appropriate screenshots
	public static void ResultScreenShot(String directory) throws IOException{
		
		ExtentTest logger1 = TestClass.logger;
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
		FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + "\\ResultScreenShots\\"+directory));
		logger1.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\ResultScreenShots\\"+directory);
		
	}
	//Method to handle alert
	public static void handleAlert() throws InterruptedException, IOException {
		
		Alert alert = driver.switchTo().alert();
		System.out.println(alert.getText());
		alert.accept();
		
	}
	//Method to check whether alert is present or not
	public static boolean isAlertPresent(){
	    boolean foundAlert = false;
	    WebDriverWait wait = new WebDriverWait(driver, 30 /*timeout in seconds*/);
	    try {
	        wait.until(ExpectedConditions.alertIsPresent());
	        foundAlert = true;
	    } catch (Exception e) {
	        foundAlert = false;
	    }
	    return foundAlert;
	}
	
	// Enter text into a field
		public void enterText(WebElement nameKey, String text) {
			nameKey.sendKeys(text);
		}

		// Click a web element
		public void clickElement(WebElement locatorKey) {

			locatorKey.click();

		}
	
	//Method to quit the browser
	public void closeBrowser() {
		driver.quit();
	}

	//PageFactory for the Hospitallist class and for next page.
	public static HospitalsList hospitals() {
		return PageFactory.initElements(driver, HospitalsList.class);
	}
}
